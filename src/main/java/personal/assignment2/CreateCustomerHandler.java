/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 作成画面の顧客操作ハンドラー（CreateCustomerHandler）
 */
package personal.assignment2;

import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Customer;
import personal.assignment2.model.PayingCustomer;
import personal.assignment2.model.Supplement;

/**
 * 作成画面における顧客関連操作を担当するハンドラー
 * @author パスコー・セオ・ジェームズ
 */
public class CreateCustomerHandler extends Handler
{   
    // 各ChoiceBoxの選択肢を表す定数
    private static final String PAYING_CUSTOMER_CHOICE = "Paying";
    private static final String ASSOCIATE_CUSTOMER_CHOICE = "Associate";
    private static final String CREDIT_CARD_CHOICE = "Credit Card";
    private static final String DIRECT_DEBIT_CHOICE = "Direct Debit";
  
    private final ListView<Customer> _customerListView;
    
    private final TextField _nameField;
    private final TextField _emailField;
    
    private final ChoiceBox<String> _typeChoiceBox;
    private final ChoiceBox<String> _paymentMethodChoiceBox;
    
    private final ComboBox<PayingCustomer> _coveringCustomerCombo;
    
    private final TextField _streetNumberField;
    private final TextField _streetNameField;
    private final TextField _suburbField;
    private final TextField _postcodeField;
    
    // 顧客のサプリメント制御用コントロール
    private final ComboBox<Supplement> _availableSupplementsCombo;
    private final Button _addSupplementButton;
    private final Button _removeSupplementButton;
    private final ListView<Supplement> _supplementListView;

    // 追加対象として選択したサプリメント
    private final ObservableList<Supplement> _chosenSupplements = FXCollections.observableArrayList();
    
    /**
     * CreateCustomerHandler クラスのコンストラクタ
     * @param inCustomerListView 顧客リストビューの FXML 要素
     * @param inNameField 顧客名フィールドの FXML 要素
     * @param inEmailField メールアドレスフィールドの FXML 要素
     * @param inTypeChoiceBox 顧客種別ChoiceBoxの FXML 要素
     * @param inPaymentMethodChoiceBox 支払い方法ChoiceBoxの FXML 要素
     * @param inCoveringCustomerCombo 費用負担元の有料顧客コンボボックスの FXML 要素
     * @param inStreetNumberField 番地フィールドの FXML 要素
     * @param inStreetNameField 通り名フィールドの FXML 要素
     * @param inSuburbField 地区フィールドの FXML 要素
     * @param inPostcodeField 郵便番号フィールドの FXML 要素
     * @param inAddSupplementButton サプリメント追加ボタンの FXML 要素
     * @param inRemoveSupplementButton サプリメント削除ボタンの FXML 要素
     * @param inAvailableSupplementCombo 利用可能なサプリメントコンボボックスの FXML 要素
     * @param inSupplementListView サプリメントリストビューの FXML 要素
     */
    public CreateCustomerHandler(
            ListView<Customer> inCustomerListView,
            TextField inNameField,
            TextField inEmailField,
            ChoiceBox<String> inTypeChoiceBox,
            ChoiceBox<String> inPaymentMethodChoiceBox,
            ComboBox<PayingCustomer> inCoveringCustomerCombo,
            TextField inStreetNumberField,
            TextField inStreetNameField,
            TextField inSuburbField,
            TextField inPostcodeField,
            Button inAddSupplementButton,
            Button inRemoveSupplementButton,
            ComboBox<Supplement> inAvailableSupplementCombo,
            ListView<Supplement> inSupplementListView
            )
    {
        _customerListView = inCustomerListView;
        _nameField = inNameField;
        _emailField = inEmailField;
        _typeChoiceBox = inTypeChoiceBox;
        _paymentMethodChoiceBox = inPaymentMethodChoiceBox;
        _coveringCustomerCombo = inCoveringCustomerCombo;
        _streetNumberField = inStreetNumberField;
        _streetNameField = inStreetNameField;
        _suburbField = inSuburbField;
        _postcodeField = inPostcodeField;
        _addSupplementButton = inAddSupplementButton;
        _removeSupplementButton = inRemoveSupplementButton;
        _availableSupplementsCombo = inAvailableSupplementCombo;
        _supplementListView = inSupplementListView;
        
        _coveringCustomerCombo.setDisable(true);
        _availableSupplementsCombo.setDisable(true);
        _addSupplementButton.setDisable(true);
        _removeSupplementButton.setDisable(true);
        
        populateChoiceBoxes();
        _supplementListView.setItems(_chosenSupplements);
    }
    
    /**
     * ChoiceBoxを初期化する
     */
    private void populateChoiceBoxes()
    {
        _paymentMethodChoiceBox.setItems(FXCollections.observableArrayList(CREDIT_CARD_CHOICE, DIRECT_DEBIT_CHOICE));
        _typeChoiceBox.setItems(FXCollections.observableArrayList(PAYING_CUSTOMER_CHOICE, ASSOCIATE_CUSTOMER_CHOICE));
      
        _paymentMethodChoiceBox.setValue(CREDIT_CARD_CHOICE);
        _typeChoiceBox.setValue(PAYING_CUSTOMER_CHOICE);
    }
    
    /**
     * 顧客リストを初期化する
     * @param magazineService 顧客を取得する雑誌サービス
     */
    private void populateCustomerList(ServiceManager magazineService)
    {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customers.addAll(magazineService.retrievePayingCustomers());
        customers.addAll(magazineService.retrieveAssociateCustomers());
        
        _customerListView.setItems(customers);
        _coveringCustomerCombo.setItems(FXCollections.observableArrayList(magazineService.retrievePayingCustomers()));
    }
    
    /**
     * 指定された顧客名が他の顧客と重複しているかどうかを確認する
     * @param nameToCheckFor 重複を確認する顧客名
     * @param magazineService 重複を確認する雑誌サービス
     * @return 重複する名前が見つかった場合は true、それ以外は false
     */
    private boolean checkForDuplicateCustomerName(String nameToCheckFor, ServiceManager magazineService) 
    {
        // 有料顧客リストと関連顧客リストの両方を検索する
        
        for (Customer customer : magazineService.retrievePayingCustomers()) 
        {
            if (customer.getName().equalsIgnoreCase(nameToCheckFor.trim()))
            {
              return true;
            }
        }
          
        for (Customer customer : magazineService.retrieveAssociateCustomers())
        {
            if (customer.getName().equalsIgnoreCase(nameToCheckFor.trim())) 
            {
                return true;
            }
        }
          
          return false;
      }
    
    /**
     * 雑誌に顧客を追加する処理
     * @param magazineService 追加先の雑誌サービス
     */
    public void addCustomerBehavior(ServiceManager magazineService)
    {   
        // 各フィールドから顧客データを取得する
        String nameData = _nameField .getText();
        String emailData = _emailField.getText();
        String streetNumberData = _streetNumberField.getText();
        String streetNameData = _streetNameField.getText();
        String suburbData = _suburbField.getText();
        String postcodeData = _postcodeField.getText();
        String typeData = _typeChoiceBox.getValue();
        ArrayList<Supplement> supplementData = new ArrayList<>(_supplementListView.getItems());
        
        // 顧客名が重複している場合は処理を中断する
        if (checkForDuplicateCustomerName(nameData, magazineService) == true)
        {
            showWarning("Duplicate customer", "Customer shares the same name as another customer, please choose a different name");
            return;
        }
        
        // 顧客が有料顧客の場合は有料顧客を作成し、それ以外は関連顧客を作成する
        if (PAYING_CUSTOMER_CHOICE.equals(typeData))
        {
            addNewPayingCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, supplementData, magazineService);
        }
        else
        {
            addNewAssociateCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, supplementData, magazineService);
        }
        
        // 顧客リストを再読み込みする
        populateCustomerList(magazineService);
        
        // 選択済みサプリメントリストと現在のサプリメント選択をクリアする
        _chosenSupplements.clear();
        _availableSupplementsCombo.getSelectionModel().clearSelection();
        
        // 注: 他のフィールドはクリアしない。同じ内容のままにしておくと、
        //     詳細を揃えた顧客を連続して追加する際に作業が速くなる。
        //     ただしテスト時には購読サプリメントなどを他の項目より頻繁に変更することが多かったため、
        //     それらのフィールドのみクリアするようにしている
    }
  
    /**
     * 雑誌サービスに新規有料顧客を追加する
     * @param nameData 有料顧客の名前
     * @param streetNumberData 有料顧客の番地
     * @param streetNameData 有料顧客の通り名
     * @param suburbData 有料顧客の地区
     * @param postcodeData 有料顧客の郵便番号
     * @param emailData 有料顧客のメールアドレス
     * @param supplementData 有料顧客が購読するサプリメント
     * @param magazineService 有料顧客を追加する雑誌サービス
     */
  private void addNewPayingCustomer(String nameData, 
          String streetNumberData, String streetNameData, String suburbData, String postcodeData, 
          String emailData, ArrayList<Supplement> supplementData, ServiceManager magazineService)
  {
      String paymentMethodData = _paymentMethodChoiceBox.getValue();
      
       magazineService.addPayingCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, paymentMethodData, supplementData);
  }
  
  /**
   * 雑誌サービスに関連顧客を追加する
   * @param nameData 関連顧客の名前
   * @param streetNumberData 関連顧客の番地
   * @param streetNameData 関連顧客の通り名
   * @param suburbData 関連顧客の地区
   * @param postcodeData 関連顧客の郵便番号
   * @param emailData 関連顧客のメールアドレス
   * @param supplementData 関連顧客が購読するサプリメント
   * @param magazineService 関連顧客を追加する雑誌サービス
   */
  private void addNewAssociateCustomer(String nameData, 
          String streetNumberData, String streetNameData, String suburbData, String postcodeData, 
          String emailData, ArrayList<Supplement> supplementData, ServiceManager magazineService)
  {
      PayingCustomer coveringCustomer = _coveringCustomerCombo.getValue();
      
      // 費用負担元の有料顧客が選択されていない状態で関連顧客の作成が試みられた場合、警告を表示して処理を中断する
      if (coveringCustomer == null)
      {
          showWarning("No covering customer", "Please select a covering cutomer");
          return;
      }
      
      magazineService.addAssociateCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, supplementData, coveringCustomer.getName());
  }
  
  /**
   * 顧客の購読サプリメントリストにサプリメントを追加する処理
   */
  public void addCustomerSupplementBehavior()
  {
    Supplement picked = _availableSupplementsCombo.getValue();
    
    // サプリメントが選択されており、かつ購読サプリメントリストにまだ含まれていない場合、サプリメントを追加する
    if (picked != null && !_chosenSupplements.contains(picked)) 
    {
        _chosenSupplements.add(picked);
    }
  }
  
  /**
   * 顧客の購読サプリメントリストからサプリメントを削除する処理
   */
  public void removeCustomerSupplementBehavior() 
  {
    Supplement toRemove = _supplementListView.getSelectionModel().getSelectedItem();
    
    // サプリメントが選択されている状態で呼び出された場合、サプリメントを削除する
    if (toRemove != null) 
    {
        _chosenSupplements.remove(toRemove);
    }
  }
  
  
  /**
   * 顧客追加時に種別（有料または関連）が変更されたときのイベント処理
   * @param obs 監視対象の値
   * @param oldType 変更前の顧客種別
   * @param newType 変更後の顧客種別
   * @param allPayingCustomers 費用負担元の有料顧客として設定可能な、雑誌サービス内のすべての有料顧客
   */
  public void customerTypeChangedBehavior(ObservableValue<? extends String> obs, String oldType, String newType, ArrayList<PayingCustomer> allPayingCustomers)
  {
      // 顧客が関連顧客に変更されたかどうか
      boolean isAssociate = ASSOCIATE_CUSTOMER_CHOICE.equals(newType);
      
      // 関連顧客モードでは費用負担元の有料顧客の選択 UI を有効化し、支払い方法の選択を無効化する
      if (isAssociate == true) 
      {
          _coveringCustomerCombo.setDisable(false);
          _paymentMethodChoiceBox.setDisable(true);
          
          // 費用負担元の有料顧客コンボボックスの選択肢を設定する
          ObservableList<PayingCustomer> coveringCustomerCandidates = FXCollections.observableArrayList(allPayingCustomers);
          _coveringCustomerCombo.setItems(coveringCustomerCandidates);
      } 
      // 有料顧客モードでは費用負担元の有料顧客の選択 UI を無効化し、支払い方法の選択を有効化する
      else
      {
          _coveringCustomerCombo.setDisable(true);
          _paymentMethodChoiceBox.setDisable(false);

          _coveringCustomerCombo.getItems().clear();
          _coveringCustomerCombo.setValue(null);
      }
  }
  
  /**
   * 選択可能なサプリメントを読み込むイベント処理
   * @param tabIsActive 顧客タブがアクティブ（選択中）かどうか
   * @param availableSupplements 雑誌サービス内で利用可能なすべてのサプリメント
   */
  public void loadAvailableSupplementsBehavior(Boolean tabIsActive, ArrayList<Supplement> availableSupplements)
  {
      // 注: タブ選択時のみ処理するため、tabIsActive で選択状態を確認している
      
      // タブが選択解除された場合は処理を終了する
      if (tabIsActive == false) 
      {
          return;
      }
      
      // 選択可能なサプリメントが存在するかどうか
      boolean supplementsAvailable; 
      
      // すべてのサプリメントをコンボボックスに設定する
      ObservableList<Supplement> allSupplements = FXCollections.observableArrayList(availableSupplements);
      _availableSupplementsCombo.setItems(allSupplements);
      
      // サプリメントが利用可能かどうかを設定する
      supplementsAvailable = !allSupplements.isEmpty();
      
      // サプリメントが利用可能かどうかに応じてサプリメント追加オプションの有効/無効を切り替える
      _availableSupplementsCombo.setDisable(!supplementsAvailable);
      _addSupplementButton.setDisable(!supplementsAvailable);
      
      _chosenSupplements.clear();
  }
}
