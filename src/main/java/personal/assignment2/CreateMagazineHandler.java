/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 作成画面の雑誌操作ハンドラー（CreateMagazineHandler）
 */

package personal.assignment2;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

import personal.assignment2.model.ServiceManager;

/**
 * 作成画面における雑誌関連操作を担当するハンドラー
 * @author パスコー・セオ・ジェームズ
 */
public class CreateMagazineHandler extends Handler
{
    // 週額料金の FXML 要素
    private final TextField _weeklyCostField;
    
    // 作成画面のサプリメントタブと顧客タブ
    private final Tab _supplementsTab;
    private final Tab _customersTab;
    
    /**
     * CreateMagazineHandler クラスのコンストラクタ
     * @param inWeeklyCostField 週額料金フィールドの FXML 要素
     * @param inSupplementsTab 作成画面のサプリメントタブ FXML 要素
     * @param inCustomersTab 作成画面の顧客タブ FXML 要素
     */
    public CreateMagazineHandler(TextField inWeeklyCostField, Tab inSupplementsTab, Tab inCustomersTab)
    {
        _weeklyCostField = inWeeklyCostField;
        _supplementsTab = inSupplementsTab;
        _customersTab = inCustomersTab;
    }
    
    /**
     * 作成した雑誌を保存する処理
     * @return 作成された雑誌サービス
     */
    public ServiceManager saveMagazineBehavior()
    {
        // 週額料金を既定値 0 に設定する
        int cost = 0;
        // 週額料金をテキストとして取得する
        String costText = _weeklyCostField.getText();
        
        // 料金を整数に解析する。
        // 料金が 0 未満の場合、または整数に解析できない場合は警告を表示する
        try
        {
            cost = Integer.parseInt(costText);
          
            if (cost < 0)
            {
                showWarning("Invalid cost", "Please ensure the cost is greater than 0");
                return null;
            }
        }
        catch (NumberFormatException exception)
        {
            showWarning("Invalid cost", "Please ensure the weekly magazine cost is a valid integer");
        }
        
        // 新しい雑誌が作成されたため、作成画面のサプリメントタブと顧客タブを有効化する
        _supplementsTab.setDisable(false);
        _customersTab.setDisable(false);
            
        return new ServiceManager(cost);
    }
}
