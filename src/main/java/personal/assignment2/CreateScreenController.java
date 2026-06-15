/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 作成画面のコントローラ（CreateScreenController）
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
 * 作成画面ビュー／FXML 用のコントローラクラス
 * @author パスコー・セオ・ジェームズ
 */
public class CreateScreenController 
{ 
  // 画面切替用のトグルグループ
  @FXML private ToggleGroup _modeToggleGroup;
  
  // 各作成モード用のタブコンテナ
  @FXML private TabPane _createTabPane;
  
  // 雑誌タブのフィールド
  @FXML private TextField _magazineWeeklyCostField;
  
  // サプリメントタブのフィールド
  @FXML private Tab _supplementsTab;
  @FXML private ListView<Supplement> _supplementListView;
  @FXML private TextField _supplementNameField;
  @FXML private TextField _supplementCostField;

  // 顧客タブのフィールド
  @FXML private Tab _customersTab;
  @FXML private ListView<Customer> _customerListView;
  @FXML private TextField _customerNameField;
  @FXML private TextField _customerEmailField;
  @FXML private ChoiceBox<String> _customerTypeChoiceBox;
  @FXML private ChoiceBox<String> _customerPaymentMethodChoiceBox;
  @FXML private ComboBox<PayingCustomer> _coveringCustomerCombo;
  @FXML private TextField _customerStreetNumberField;
  @FXML private TextField _customerStreetNameField;
  @FXML private TextField _customerSuburbField;
  @FXML private TextField _customerPostcodeField;
  
  // 顧客のサプリメント制御用フィールド
  @FXML private ComboBox<Supplement> _customerAvailableSupplementsCombo;
  @FXML private Button _customerAddSupplementButton;
  @FXML private ListView<Supplement> _customerSupplementListView;
  @FXML private Button _customerRemoveSupplementButton;
  
  // 作成された雑誌サービス
  private ServiceManager _magazineService; 
  
  // 作成画面用のハンドラー
  private CreateMagazineHandler _magazineHandler;
  private CreateSupplementHandler _supplementHandler;
  private CreateCustomerHandler _customerHandler;
  
  /**
   * 作成画面読み込み時に呼び出される JavaFX の initialize メソッド。
   * 作成画面とそのハンドラーを設定する
   */
  @FXML
  private void initialize()
  {
      // 作成された雑誌サービスを null に設定し、まだ作成されていないことを示す
      _magazineService = null;
      
      // 新しい雑誌が作成されるまで、サプリメントと顧客は追加できない。
      // これらは雑誌ハンドラー内で有効化される
      _supplementsTab.setDisable(true);
      _customersTab.setDisable(true);
            
      _magazineHandler = new CreateMagazineHandler(_magazineWeeklyCostField, _supplementsTab, _customersTab);
      _supplementHandler = new CreateSupplementHandler(_supplementListView, _supplementNameField, _supplementCostField);
      _customerHandler = new CreateCustomerHandler
        (
            _customerListView, 
            _customerNameField, _customerEmailField,
            _customerTypeChoiceBox, _customerPaymentMethodChoiceBox, 
            _coveringCustomerCombo,
            _customerStreetNumberField, _customerStreetNameField, _customerSuburbField, _customerPostcodeField,
            _customerAddSupplementButton,
            _customerRemoveSupplementButton,
            _customerAvailableSupplementsCombo,
            _customerSupplementListView
        );
      
      setupEvents();
  }
  
  /**
   * 表示画面へのナビゲーションボタンがクリックされたときのコールバック関数
   * @throws IOException 表示画面を読み込めない場合
   */
  @FXML
  private void onView() throws IOException
  {
        screenExitBehavior();
        App.setRoot("ViewScreen");
  }
  
  /**
   * 編集画面へのナビゲーションボタンがクリックされたときのコールバック関数
   * @throws IOException 編集画面を読み込めない場合
   */
  @FXML
  private void onEdit() throws IOException
  {
        screenExitBehavior();
        App.setRoot("EditScreen");
  }
  
  /**
   * 作成画面を終了するときに実行する処理を定義するメソッド
   */
  private void screenExitBehavior()
  {
      // 雑誌サービスが作成されていた場合、アプリケーションの雑誌サービスを
      // 作成したものに設定する
      if (_magazineService != null)
      {
          App.setMagazineService(_magazineService);
      }
  }
  
  /**
   * 作成画面のイベントを設定するメソッド
   */
  private void setupEvents()
  {
      _customerTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> _customerHandler.customerTypeChangedBehavior(obs, oldType, newType, _magazineService.retrievePayingCustomers()));
      
      // 顧客タブが有効化されたとき（雑誌保存後など）に呼び出されるイベント:
      _customersTab.selectedProperty().addListener((obs, wasSelected, tabIsActive) -> _customerHandler.loadAvailableSupplementsBehavior(tabIsActive, _magazineService.retrieveSupplements()));
      
      // 選択状態に応じて削除ボタンを有効／無効にする:
      _customerSupplementListView.getSelectionModel().selectedItemProperty().addListener((o, oldS, newS) -> _customerRemoveSupplementButton.setDisable(newS == null));
  }
  
  /**
   * 雑誌保存ボタンがクリックされたときのコールバック関数
   */
  @FXML
  private void onSaveMagazine()
  {
      _magazineService = _magazineHandler.saveMagazineBehavior();
  }
  
  /**
   * サプリメント追加ボタンがクリックされたときのコールバック関数
   */
  @FXML
  private void onAddSupplement()
  {
      _supplementHandler.addSupplementBehavior(_magazineService);
  }
  
  /**
   * 顧客追加ボタンがクリックされたときのコールバック関数
   */
  @FXML
  private void onAddCustomer()
  {
      _customerHandler.addCustomerBehavior(_magazineService);
  }
  
  /**
   * 顧客へのサプリメント追加ボタンがクリックされたときのコールバック関数
   */
  @FXML
  private void onAddCustSupp()
  {
      _customerHandler.addCustomerSupplementBehavior();
  }
  
  /**
   * 顧客からのサプリメント削除ボタンがクリックされたときのコールバック関数
   */
  @FXML
  private void onRemoveCustSupp() 
  {
      _customerHandler.removeCustomerSupplementBehavior();
  }
}
