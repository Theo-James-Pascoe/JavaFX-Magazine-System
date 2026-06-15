/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 作成画面のサプリメント操作ハンドラー（CreateSupplementHandler）
 */

package personal.assignment2;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Supplement;

/**
 * 作成画面におけるサプリメント関連操作を担当するハンドラー
 * @author パスコー・セオ・ジェームズ
 */
public class CreateSupplementHandler extends Handler
{
    // サプリメントリストの FXML 要素
    private final ListView<Supplement> _listView;
    // サプリメント名と週額料金の入力フィールド FXML 要素
    private final TextField _nameField;
    private final TextField _costField;
    
    /**
     * CreateSupplementHandler クラスのコンストラクタ
     * @param inListView サプリメントリストビューの FXML 要素
     * @param inNameField 名前フィールドの FXML 要素
     * @param inCostField 週額料金フィールドの FXML 要素
     */
    public CreateSupplementHandler(ListView<Supplement> inListView, TextField inNameField, TextField inCostField)
    {
        _listView = inListView;
        _nameField = inNameField;
        _costField = inCostField;
    }
    
    /**
     * サプリメントリストを初期化するメソッド
     * @param allSupplements 雑誌サービス内のサプリメント
     */
    private void populateSupplementList(ArrayList<Supplement> allSupplements)
    {
        ObservableList<Supplement> supplements = FXCollections.observableArrayList(allSupplements);
        _listView.setItems(supplements);
    }
    
    /**
     * 指定されたサプリメント名が他のサプリメントと重複しているかどうかを確認するメソッド
     * @param nameToCheckFor 重複を確認する名前
     * @param magazineService 重複を検索する雑誌サービス
     * @return 重複する名前が見つかった場合は true、それ以外は false
     */
    private boolean checkForDuplicateSupplementName(String nameToCheckFor, ServiceManager magazineService) 
    {
        // 雑誌内のすべてのサプリメントを検索し、重複する名前が見つかった場合は true を返す
        for (Supplement currentSupplement : magazineService.retrieveSupplements())
        {
            if (currentSupplement.getName().equalsIgnoreCase(nameToCheckFor.trim()))
            {
                return true;
            }
        }
        
        // 重複が見つからなかった場合は false を返す
        return false;
    }
    
    /**
     * 雑誌サービスにサプリメントを追加するメソッド
     * @param magazineService サプリメントを追加する雑誌サービス
     */
    public void addSupplementBehavior(ServiceManager magazineService)
    {
        // 入力フィールドからサプリメントの名前と料金を取得する
        String nameData = _nameField.getText().trim();
        String costText = _costField.getText().trim();
        
        // サプリメントの料金（整数）
        int costData;
        
        // 名前または週額料金の入力が空の場合、警告を表示して処理を中断する
        if (nameData.isEmpty() || costText.isEmpty()) 
        {
            showWarning("Invalid Input", "Name and cost must not be empty.");
            return;
        }
        
        // 入力されたサプリメント名が重複している場合、警告を表示して処理を中断する
        if (checkForDuplicateSupplementName(nameData, magazineService) == true)
        {
            showWarning("Duplicate supplement", "Supplement shares the same name as another supplement, please choose a different name");
            return;
        }
      
        // 料金テキストを整数に解析する。入力が有効な整数でない場合、
        // または値が 0 未満の場合は警告を表示する
        try 
        {
            costData = Integer.parseInt(costText);
            
            if (costData < 0) 
            {
                showWarning("Invalid Cost", "Weekly cost must be a positive number.");
                return;
            }
        }
        catch (NumberFormatException exception) 
        {
            showWarning("Invalid Input", "Cost must be a valid integer.");
            return;
        }
        
        // 雑誌サービスにサプリメントを追加する
        magazineService.addSupplement(nameData, costData);
        
        // サプリメントリストを再設定する
        populateSupplementList(magazineService.retrieveSupplements());
    }
}
