/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 編集画面のサプリメント操作ハンドラー（EditSupplementHandler）
 */

package personal.assignment2;

import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Supplement;

/**
 * 編集画面におけるサプリメント関連操作を担当するハンドラー
 * @author パスコー・セオ・ジェームズ
 */
public class EditSupplementHandler extends Handler
{
    // 右側の編集ペインとサプリメントタブ
    private final TabPane _editingPane;
    private final Tab _editingTab;
    
    // 左側のサプリメントリスト
    private final ListView<Supplement> _listView;

    // サプリメントフォームのフィールド
    private final TextField _nameField;
    private final TextField _costField;
    
    /**
     * EditSupplementHandler クラスのコンストラクタ
     * @param inEditingPane 編集ペインの FXML 要素
     * @param inCustomerTab サプリメントタブの FXML 要素
     * @param inListView リストビューの FXML 要素
     * @param inNameField 名前フィールドの FXML 要素
     * @param inCostField 週額料金フィールドの FXML 要素
     * @param magazineService 編集対象の雑誌サービス
     */
    public EditSupplementHandler(TabPane inEditingPane, Tab inCustomerTab,ListView<Supplement> inListView, TextField inNameField, TextField inCostField, ServiceManager magazineService)
    {
        _editingPane = inEditingPane;
        _editingTab = inCustomerTab;
        _listView = inListView;
        _nameField = inNameField;
        _costField = inCostField;
        
        // リストを初期化し、イベントを設定する
        populateList(magazineService.retrieveSupplements());
        setupEvents();
    }
    
    /**
     * 左側のサプリメントリストを初期化するメソッド
     * @param allSupplements リストに設定するサプリメント
     */
    private void populateList(ArrayList<Supplement> allSupplements)
    {
        ObservableList<Supplement> supplements = FXCollections.observableArrayList(allSupplements);
        
        _listView.setItems(supplements);
    }
    
    /**
     * 編集画面のサプリメント関連イベントを設定するメソッド
     */
    private void setupEvents()
    {
        // 選択時にフォームへ読み込むリスナー
        _listView.getSelectionModel().selectedItemProperty().addListener(this::supplementSelectBehavior);
    }
    
    /**
     * サプリメントが選択されたときのイベント処理
     * @param observable 監視対象のサプリメント値
     * @param oldValue イベントの変更前のサプリメント値
     * @param newValue イベントの変更後のサプリメント値
     */
    private void supplementSelectBehavior(ObservableValue<? extends Supplement> observable, Supplement oldValue, Supplement newValue) 
    {
        // サプリメントが正しく選択された状態で呼び出された場合
        if (newValue != null) 
        {
            // 右側の編集ペインでサプリメント編集タブを選択する
            _editingPane.getSelectionModel().select(_editingTab);
            // 選択されたサプリメントを読み込む
            loadSupplement(newValue);
        }
    }
    
    /**
     * サプリメントの詳細をサプリメント編集 UI に読み込むメソッド
     * @param supplementToLoad 読み込むサプリメント
     */
    private void loadSupplement(Supplement supplementToLoad) 
    {
        _nameField.setText(supplementToLoad.getName());
        _costField.setText(String.valueOf(supplementToLoad.getWeeklyCost()));
    }
    
    /**
     * 編集画面で新しいサプリメントを追加するメソッド
     * @param magazineService 左側のサプリメントリストを再設定する際に使用する雑誌サービス
     */
    public void supplementAddingBehavior(ServiceManager magazineService)
    {
        // 仮のサプリメントとして空のサプリメントを追加する
        magazineService.addSupplement("", 0);
        
        // サプリメントリストを再設定する
        populateList(magazineService.retrieveSupplements());
        
        // 新しいサプリメントを選択する
        _listView.getSelectionModel().selectLast();
    }
    
    /**
     * 雑誌サービスからサプリメントを削除するメソッド
     * @param magazineService 削除対象の雑誌サービス
     */
    public void supplementDeletionBehavior(ServiceManager magazineService)
    {
        // 現在選択中のサプリメントを削除対象として取得する
        Supplement supplementToDelete = _listView.getSelectionModel().getSelectedItem();
        
        // 削除対象のサプリメントが正しく見つかった場合
        if (supplementToDelete != null)
        {
            // サプリメントを削除し、左側のリストを再設定する
            magazineService.removeSupplement(supplementToDelete.getName());
            populateList(magazineService.retrieveSupplements());
        }
    }
    
    /**
     * 雑誌サービス内のサプリメント情報を保存するメソッド
     * @param magazineService 詳細を保存する雑誌サービス
     */
    public void supplementSavingBehavior(ServiceManager magazineService)
    {
        // リストビューから現在選択中のサプリメントを取得する
        Supplement selectedSupplement = _listView.getSelectionModel().getSelectedItem();
        
        // 選択中のサプリメントを正しく取得できなかった場合は処理を中断する
        if (selectedSupplement == null)
        {
            return;
        }
        
        // 編集 UI フィールドの名前と週額料金テキストを取得する
        String nameData = _nameField.getText().trim();
        String costText = _costField.getText().trim();
        
        // サプリメントの週額料金
        int costData;
        
        // 名前または週額料金の入力が空の場合、警告を表示して処理を中断する
        if (nameData.isEmpty() || costText.isEmpty()) 
        {
            showWarning("Invalid Input", "Name and cost must not be empty.");
            return;
        }
        
        // 料金を整数に解析する。
        // 結果が 0 未満の場合、または有効な整数に変換できない場合は警告を表示する
        try 
        {
            costData = Integer.parseInt(costText);
            
            if (costData < 0) 
            {
                showWarning("Invalid Cost", "Weekly cost must be a positive number.");
                return;
            }
        }
        catch (NumberFormatException e) 
        {
            showWarning("Invalid Input", "Cost must be a valid integer.");
            return;
        }
        
        // 選択中のサプリメントの名前と週額料金を入力値に設定する
        selectedSupplement.setName(nameData);
        selectedSupplement.setWeeklyCost(costData);
        
        // リストビューを再設定／更新し、保存したサプリメントを選択する
        populateList(magazineService.retrieveSupplements());
        _listView.getSelectionModel().select(selectedSupplement);
    }
}
