/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 編集画面のコントローラ（EditScreenController）
 */

package personal.assignment2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Supplement;
import personal.assignment2.model.Customer;
import personal.assignment2.model.PayingCustomer;

/**
 * 編集画面ビュー／FXML 用のコントローラクラス
 * @author パスコー・セオ・ジェームズ
 */
public class EditScreenController 
{   
    // 編集画面のサプリメントタブと顧客タブ
    @FXML private TabPane _editTabPane;
    @FXML private Tab _customerTab;
    @FXML private Tab _supplementTab;
    
    // 左側のリスト
    @FXML private ListView<Supplement> _supplementListView;
    @FXML private ListView<Customer> _customerListView;

    // サプリメントフォームのフィールド
    @FXML private TextField _supplementNameField;
    @FXML private TextField _supplementCostField;

    // 顧客フォームのフィールド
    @FXML private TextField _customerNameField;
    @FXML private TextField _customerStreetNumberField;
    @FXML private TextField _customerStreetNameField;
    @FXML private TextField _customerSuburbField;
    @FXML private TextField _customerPostcodeField;
    @FXML private TextField _customerEmailField;
    @FXML private ChoiceBox<String> _customerPaymentMethodChoiceBox;
    @FXML private ChoiceBox<String> _customerTypeChoice;
    @FXML private ComboBox<PayingCustomer> _coveringCustomerCombo;
    
    // 編集対象の雑誌サービス
    private ServiceManager _magazineService;
    
    // 編集画面用のハンドラー
    private EditSupplementHandler _supplementHandler;
    private EditCustomerHandler _customerHandler;
    
    /**
     * 編集画面を設定する JavaFX の initialize メソッド
     */
    @FXML
    private void initialize() 
    {
        // 編集対象の現在の雑誌サービスを取得する
        _magazineService = App.getMagazineService();
        
        _supplementHandler = new EditSupplementHandler(_editTabPane, _supplementTab, _supplementListView, _supplementNameField, _supplementCostField, _magazineService);
        
        _customerHandler = new EditCustomerHandler
        (
                _editTabPane,
                _customerTab,
                _customerListView, 
                _customerNameField,
                _customerStreetNumberField,
                _customerStreetNameField,
                _customerSuburbField,
                _customerPostcodeField,
                _customerEmailField,
                _customerPaymentMethodChoiceBox,
                _customerTypeChoice,
                _coveringCustomerCombo,
                _magazineService
        );
    }
    
    /**
     * 表示画面へのナビゲーションボタンがクリックされたときのコールバック関数
     * @throws IOException 表示画面を開けない場合
     */
    @FXML 
    private void onView() throws IOException
    {
        App.setRoot("ViewScreen");
    }
    
    /**
     * 作成画面へのナビゲーションボタンがクリックされたときのコールバック関数
     * @throws IOException 作成画面を開けない場合
     */
    @FXML 
    private void onCreate() throws IOException 
    {
        App.setRoot("CreateScreen");
    }

    /**
     * サプリメント追加ボタンがクリックされたときのコールバック関数
     */
    @FXML 
    private void onAddSupplement()
    {
        _supplementHandler.supplementAddingBehavior(_magazineService);
    }
    
    /**
     * サプリメント削除ボタンがクリックされたときのコールバック関数
     */
    @FXML 
    private void onDeleteSupplement()
    {
        _supplementHandler.supplementDeletionBehavior(_magazineService);
    }
    
    /**
     * サプリメント保存ボタンがクリックされたときのコールバック関数
     */
    @FXML 
    private void onSaveSupplement()
    {
        _supplementHandler.supplementSavingBehavior(_magazineService);
    }
    
    /**
     * 顧客追加ボタンがクリックされたときのコールバック関数
     */
    @FXML
    private void onAddCustomer()
    {
        _customerHandler.customerAddingBehavior();
    }
    
    /**
     * 顧客削除ボタンがクリックされたときのコールバック関数
     */
    @FXML
    private void onDeleteCustomer() 
    {
        _customerHandler.customerDeletionBehavior(_magazineService);
    }
    
    /**
     * 顧客保存ボタンがクリックされたときのコールバック関数
     */
    @FXML
    private void onSaveCustomer()
    {
        _customerHandler.customerSavingBehavior(_magazineService);
    }
}
