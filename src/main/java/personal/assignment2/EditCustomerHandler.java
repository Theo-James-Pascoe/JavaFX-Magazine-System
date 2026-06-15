/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 編集画面の顧客操作ハンドラー（EditCustomerHandler）
 */
package personal.assignment2;

import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Customer;
import personal.assignment2.model.PayingCustomer;
import personal.assignment2.model.AssociateCustomer;
import personal.assignment2.model.Address;
import personal.assignment2.model.EmailAddress;
import personal.assignment2.model.PaymentMethod;
import personal.assignment2.model.Supplement;

/**
 * 編集画面における顧客関連操作を担当するハンドラー
 * @author パスコー・セオ・ジェームズ
 */
public class EditCustomerHandler extends Handler
{
    // 各ChoiceBoxの選択肢を表す定数
    private static final String PAYING_CUSTOMER_CHOICE = "Paying";
    private static final String ASSOCIATE_CUSTOMER_CHOICE = "Associate";
    private static final String CREDIT_CARD_CHOICE = "Credit Card";
    private static final String DIRECT_DEBIT_CHOICE = "Direct Debit";
    
    // 右側の編集パネルおよび顧客タブ
    private final TabPane _editingPane;
    private final Tab _editingTab;
    
    // 左側の顧客リスト
    private final ListView<Customer> _listView;

    // 顧客フォームの入力フィールド
    private final TextField _nameField;
    private final TextField _streetNumberField;
    private final TextField _streetNameField;
    private final TextField _suburbField;
    private final TextField _postcodeField;
    private final TextField _emailField;
    private final ChoiceBox<String> _paymentMethodChoiceBox;
    private final ChoiceBox<String> _typeChoiceBox;
    private final ComboBox<PayingCustomer> _coveringCustomerCombo;
    
    // 編集中の顧客が新規顧客かどうか
    private boolean _isNewCustomer;
    
    /**
     * EditCustomerHandler クラスのコンストラクタ
     * @param inEditingPane 編集ペインの FXML 要素
     * @param inCustomerTab 顧客タブの FXML 要素
     * @param inListView 左側の顧客リストの FXML 要素
     * @param inNameField 顧客名フィールドの FXML 要素
     * @param inStreetNumberField 番地フィールドの FXML 要素
     * @param inStreetNameField 通り名フィールドの FXML 要素
     * @param inSuburbField 地区フィールドの FXML 要素
     * @param inPostcodeField 郵便番号フィールドの FXML 要素
     * @param inEmailField メールアドレスフィールドの FXML 要素
     * @param inPaymentMethodChoiceBox 支払い方法ChoiceBoxの FXML 要素
     * @param inTypeChoiceBox 顧客種別ChoiceBoxの FXML 要素
     * @param inCoveringCustomerCombo 費用負担元の有料顧客コンボボックスの FXML 要素
     * @param magazineService 編集対象の雑誌サービス
     */
    public EditCustomerHandler
        (
            TabPane inEditingPane,
            Tab inCustomerTab,
            ListView<Customer> inListView, 
            TextField inNameField, 
            TextField inStreetNumberField,
            TextField inStreetNameField,
            TextField inSuburbField, 
            TextField inPostcodeField, 
            TextField inEmailField, 
            ChoiceBox<String> inPaymentMethodChoiceBox,
            ChoiceBox<String> inTypeChoiceBox,
            ComboBox<PayingCustomer> inCoveringCustomerCombo,
            ServiceManager magazineService
        )
    {
        _editingPane = inEditingPane;
        _editingTab = inCustomerTab;
        _listView = inListView;
        _nameField = inNameField;
        _streetNumberField = inStreetNumberField;
        _streetNameField = inStreetNameField;
        _suburbField = inSuburbField;
        _postcodeField = inPostcodeField;
        _emailField = inEmailField;
        _paymentMethodChoiceBox = inPaymentMethodChoiceBox;
        _typeChoiceBox = inTypeChoiceBox;
        _coveringCustomerCombo = inCoveringCustomerCombo;
        
        _isNewCustomer = false;
        
        // 左側の顧客リストを初期化し、必要なイベントを設定する
        populateCustomerDisplays(magazineService.retrievePayingCustomers(), magazineService.retrieveAssociateCustomers());
        populateChoiceBoxes();
        setupEvents(magazineService);
    }
    
    /**
     * 顧客が表示される領域（左側の顧客リストおよび費用負担元の有料顧客コンボボックス）を初期化する
     * @param allPayingCustomers 雑誌内のすべての有料顧客
     * @param allAssociateCustomers 雑誌内のすべての関連顧客
     */
    private void populateCustomerDisplays(ArrayList<PayingCustomer> allPayingCustomers, ArrayList<AssociateCustomer> allAssociateCustomers)
    {
        // すべての顧客のリストを作成する
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers.addAll(allPayingCustomers);
        allCustomers.addAll(allAssociateCustomers);
        
        // 左側にすべての顧客リストを設定する
        _listView.setItems(allCustomers);
        
        // 費用負担元の有料顧客の選択肢をすべての有料顧客に設定する
        _coveringCustomerCombo.setItems(FXCollections.observableArrayList(allPayingCustomers));
    }
    
    /**
     * ChoiceBoxを初期化する
     */
    private void populateChoiceBoxes()
    {
        _paymentMethodChoiceBox.setItems(FXCollections.observableArrayList(CREDIT_CARD_CHOICE, DIRECT_DEBIT_CHOICE));
        _typeChoiceBox.setItems(FXCollections.observableArrayList(PAYING_CUSTOMER_CHOICE, ASSOCIATE_CUSTOMER_CHOICE));
    }
    
    /**
     * 顧客関連の編集画面イベントを設定する
     * @param magazineService イベント設定に使用する雑誌サービス
     */
    private void setupEvents(ServiceManager magazineService)
    {
        _listView.getSelectionModel().selectedItemProperty().addListener(this::customerSelectBehavior);
        
        // 有料顧客が関連顧客に変更された場合のイベントリスナー
        // 編集中の有料顧客を費用負担元の有料顧客の選択肢リストから除外する
        // （通常はすべての有料顧客を表示）エラーを防ぐため
        _typeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> customerTypeChangedBehavior(obs, oldType, newType, magazineService.retrievePayingCustomers()));
    }
    
    /**
     * 顧客が選択されたときのイベント処理
     * @param observable 監視対象の顧客値
     * @param oldValue 変更前の顧客値
     * @param newValue 変更後の顧客値
     */
    private void customerSelectBehavior(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue)
    {
        // 右側の編集ペインで顧客編集タブを選択する
        _editingPane.getSelectionModel().select(_editingTab);
        // 選択された顧客を読み込む
        loadCustomer(newValue);
    }
    
    /**
     * 編集時に顧客種別が変更されたときのイベント処理
     * @param observable 監視対象の値
     * @param oldValue 変更前の種別
     * @param newValue 変更後の種別
     * @param allPayingCustomers 有料顧客リストの初期化に使用する、雑誌サービス内のすべての有料顧客
     */
    private void customerTypeChangedBehavior(ObservableValue<? extends String> observable, String oldType, String newType, ArrayList<PayingCustomer> allPayingCustomers)
    {
        // 種別が関連顧客に変更されたかどうか
        boolean isAssociate = ASSOCIATE_CUSTOMER_CHOICE.equals(newType);
        
        // 編集中の顧客
        Customer editingCustomer;
        // 費用負担元の有料顧客の選択肢
        ObservableList<PayingCustomer> coveringCustomerOptions;
        
        // 顧客が関連顧客に変更された場合
        if (isAssociate == true)
        {
            // 費用負担元の有料顧客コンボボックスを有効化し、支払い方法ChoiceBoxを無効化する
            _coveringCustomerCombo.setDisable(false);
            _paymentMethodChoiceBox.setDisable(true);
            
            // 編集中の顧客を現在選択中の顧客に設定する
            editingCustomer = _listView.getSelectionModel().getSelectedItem();
            // すべての有料顧客から費用負担元の有料顧客の選択肢リストを作成する
            coveringCustomerOptions = FXCollections.observableArrayList(allPayingCustomers);
            // 編集中の顧客を選択肢リストから除外する（保存後は有料顧客ではなくなるため）
            
            // 顧客情報はまだ保存されていないため、雑誌サービスにはこの変更が反映されていない。
            // フィルタリングせずすべての有料顧客を表示すると、編集中の顧客が自分自身の費用負担元の有料顧客として
            // 設定できてしまう可能性がある
            coveringCustomerOptions.removeIf(payer -> payer.getName().equals(editingCustomer.getName()));
            // 費用負担元の有料顧客コンボボックスに選択肢を設定する
            _coveringCustomerCombo.setItems(coveringCustomerOptions);
        }
        // 顧客が有料顧客に変更された場合
        else
        {
            // 費用負担元の有料顧客コンボボックスを無効化し、支払い方法ChoiceBoxを有効化する
            _coveringCustomerCombo.setDisable(true);
            _paymentMethodChoiceBox.setDisable(false);
            
            // 費用負担元の有料顧客の選択肢をクリアし、現在の選択値を null に設定する
            _coveringCustomerCombo.getItems().clear();
            _coveringCustomerCombo.setValue(null);
        }
    }
    
    /**
     * 編集画面で顧客が追加されたときの処理
     */
    public void customerAddingBehavior()
    {
        // プレースホルダーの有料顧客を作成する
        PayingCustomer newCustomerPlaceholder = new PayingCustomer("New", new Address("","","",""), new EmailAddress(""), PaymentMethod.CreditCard);
        // 編集中の顧客が新規の有料顧客となるため、新規顧客フラグを true に設定する
        _isNewCustomer = true;
        
        // リストを更新し、新しいプレースホルダー顧客を選択する
        _listView.getItems().add(newCustomerPlaceholder);
        _listView.getSelectionModel().select(newCustomerPlaceholder);
        
        // フォームをクリアし、既定値を設定して各入力欄を有効化する
        clearCustomerForm();
        _paymentMethodChoiceBox.setDisable(false);
        _paymentMethodChoiceBox.setValue(CREDIT_CARD_CHOICE);
        _typeChoiceBox.setValue(PAYING_CUSTOMER_CHOICE);
        _coveringCustomerCombo.setDisable(true);
    }
    
    /**
     * 雑誌サービスから顧客を削除する処理
     * @param magazineService 顧客を削除する雑誌サービス
     */
    public void customerDeletionBehavior(ServiceManager magazineService)
    {
        // 現在選択中の顧客を取得する
        Customer selectedCustomer = _listView.getSelectionModel().getSelectedItem();
        
        // 選択中の顧客が null の場合は警告を表示して処理を終了する
        if (selectedCustomer == null)
        {
            showWarning("No customer to delete", "Please select a customer to delete");
            return;
        }
        
        // 本当に削除するか確認する確認ダイアログを表示する
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selectedCustomer.getName() + "?", ButtonType.YES, ButtonType.NO);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Customer");
        confirmationAlert.showAndWait();
        
        // ユーザーがキャンセルした、または「いいえ」を押した場合はここで終了する
        if (confirmationAlert.getResult() == ButtonType.NO)
        {
            return;
        }
        
        // 削除対象が関連顧客の場合は、すぐに削除する
        if (selectedCustomer instanceof AssociateCustomer)
        {
            magazineService.removeAssociateCustomer(selectedCustomer.getName());
        }
        // 削除対象が有料顧客の場合は、関連顧客がいないことを確認してから削除する
        else if (selectedCustomer instanceof PayingCustomer)
        {
            if (checkForAssociates((PayingCustomer) selectedCustomer, magazineService.retrieveAssociateCustomers()) == true)
            {
                return;
            }
            
            magazineService.removePayingCustomer(selectedCustomer.getName());
        }
        
        // 顧客が表示される領域を再初期化する
        populateCustomerDisplays(magazineService.retrievePayingCustomers(), magazineService.retrieveAssociateCustomers());
        // 顧客編集フォームをクリアする
        clearCustomerForm();
        _coveringCustomerCombo.setValue(null);
        _typeChoiceBox.setValue(null);
    }
    
    /**
     * 指定した有料顧客に紐づく関連顧客がいるかどうかを確認する
     * @param customerToCheckFor 関連顧客の有無を確認する有料顧客
     * @param allAssociateCustomers 雑誌サービス内のすべての関連顧客
     * @return 関連顧客が存在する場合は true、それ以外は false
     */
    private boolean checkForAssociates(PayingCustomer customerToCheckFor, ArrayList<AssociateCustomer> allAssociateCustomers)
    {
        // デフォルトでは関連顧客がいないと仮定する
        boolean hasAssociates = false;
        
        // すべての関連顧客を走査する
        for (AssociateCustomer currentCustomer : allAssociateCustomers)
        {
            // 現在の関連顧客の費用負担元の有料顧客を取得する
            PayingCustomer coveringCustomer = currentCustomer.getCoveringCustomer();
            
            // 現在の関連顧客が、確認対象と同名の有料顧客の費用負担の対象になっている場合
            if (coveringCustomer != null && coveringCustomer.getName().equals(customerToCheckFor.getName()))
            {
                // 関連顧客が存在すると判断し、ループを終了する
                hasAssociates = true;
                break;
            }
        }
        
        // ループ後に関連顧客が検出された場合は警告を表示する
        if (hasAssociates == true)
        {
            showWarning("Illegal deletion", "This paying customer has associated customers, please remove them first before deleting.");                
        }
        
        // 有料顧客に関連顧客がいたかどうかを返す
        return hasAssociates;
    }
    
    /**
     * 顧客に対する変更を保存する処理
     * @param magazineService 変更を保存する雑誌サービス
     */
    public void customerSavingBehavior(ServiceManager magazineService)
    {
        // 選択中の顧客を取得する
        Customer selectedCustomer = _listView.getSelectionModel().getSelectedItem();
        
        // 顧客が正しく選択されていない場合は処理を終了する
        if (selectedCustomer == null)
        {
            return;
        }
        
        // 選択中の顧客に入力されたデータを取得する
        String nameData = _nameField .getText();
        String emailData = _emailField.getText();
        String streetNumberData = _streetNumberField.getText();
        String streetNameData = _streetNameField.getText();
        String suburbData = _suburbField.getText();
        String postcodeData = _postcodeField.getText();
        String typeData = _typeChoiceBox.getValue();
        
        // 現在選択中の顧客の支払い方法データ
        String paymentMethodData;
        
        // 保存対象が新規顧客の場合
        if (_isNewCustomer == true)
        {
            // 選択中の顧客に対応する新規顧客を作成する
            selectedCustomer = createNewCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, typeData, magazineService);
        }
        // 保存対象が既存顧客の場合
        else
        {
            // 選択中の顧客が有料顧客であり、編集 UI の種別入力も有料顧客の場合
            if (selectedCustomer instanceof PayingCustomer && typeData.equals(PAYING_CUSTOMER_CHOICE))
            {
                // UI から顧客の支払い方法入力データを取得する
                paymentMethodData = _paymentMethodChoiceBox.getValue();
                // 有料顧客を保存する
                saveExistingPayingCustomer((PayingCustomer) selectedCustomer, nameData, emailData, paymentMethodData, streetNumberData, streetNameData, suburbData, postcodeData);
            }
            // 選択中の顧客が関連顧客であり、編集 UI の種別入力も関連顧客の場合
            else if (selectedCustomer instanceof AssociateCustomer && typeData.equals(ASSOCIATE_CUSTOMER_CHOICE))
            {
                // 関連顧客を保存する
                saveExistingAssociateCustomer((AssociateCustomer) selectedCustomer, nameData, emailData, streetNumberData, streetNameData, suburbData, postcodeData);
            }
            // 顧客の種別が編集 UI の種別入力と一致しない場合、種別が変更されたことを意味する
            else
            {
                // 種別変更に伴う調整を考慮して顧客を保存する
                saveExistingChangedCustomerType(selectedCustomer, nameData, emailData, streetNumberData, streetNameData, suburbData, postcodeData, typeData, magazineService);
            }
        }
        
        // 顧客表示領域を再初期化する
        populateCustomerDisplays(magazineService.retrievePayingCustomers(), magazineService.retrieveAssociateCustomers());
        
        // 保存した顧客を選択し、変更を反映するためにリストを更新する
        _listView.getSelectionModel().select(selectedCustomer);
        _listView.refresh();
        
        // 保存した顧客を読み込む
        loadCustomer(selectedCustomer);
    }
    
    /**
     * 雑誌サービスに新規顧客を作成する
     * @param nameData 顧客名の入力データ
     * @param streetNumberData 番地の入力データ
     * @param streetNameData 通り名の入力データ
     * @param suburbData 地区の入力データ
     * @param postcodeData 郵便番号の入力データ
     * @param emailData メールアドレスの入力データ
     * @param typeData 顧客種別の入力データ
     * @param magazineService 顧客を追加する雑誌サービス
     * @return 作成された顧客
     */
    private Customer createNewCustomer(String nameData, 
            String streetNumberData, String streetNameData, String suburbData, String postcodeData, 
            String emailData, String typeData, ServiceManager magazineService)
    {
        // 作成された顧客
        Customer createdCustomer;
        // 関連顧客の場合の費用負担元の有料顧客
        PayingCustomer coveringCustomer;
        // 支払い方法の入力データ
        String paymentMethodData = _paymentMethodChoiceBox.getValue();
        
        
        // 顧客作成時に使用する空のサプリメントリスト
        ArrayList<Supplement> noSupplements = new ArrayList<>();
        
        // 作成する顧客の種別入力が有料顧客の場合、入力データで新規有料顧客を雑誌サービスに追加する
        if (PAYING_CUSTOMER_CHOICE.equals(typeData))
        {
            createdCustomer = magazineService.addPayingCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, paymentMethodData, noSupplements);
        }
        // 作成する顧客の種別入力が関連顧客の場合、入力データで新規関連顧客を雑誌サービスに追加する
        else
        {
            // 費用負担元の有料顧客を入力値から設定する
            coveringCustomer = _coveringCustomerCombo.getValue();
            
            // 費用負担元の有料顧客なしで関連顧客を作成しようとした場合は警告を表示し、null を返して終了する
            if (coveringCustomer == null)
            {
                showWarning("No covering customer", "Please select a covering cutomer");
                return null;
            }
            
            // 入力データで新規関連顧客を作成する
            createdCustomer = magazineService.addAssociateCustomer(nameData, streetNumberData, streetNameData, suburbData, postcodeData, emailData, noSupplements, coveringCustomer.getName());
        }
        
        // 新規顧客の追加が完了したため、新規顧客フラグをリセットする
        _isNewCustomer = false;
        
        // 新規作成された顧客を返す
        return createdCustomer;
    }

    /**
     * 既存の有料顧客の編集データを保存する
     * @param customerToSave 編集データを保存する有料顧客
     * @param inName 保存する有料顧客の名前入力データ
     * @param inEmail 保存する有料顧客のメールアドレス入力データ
     * @param inPaymentMethod 保存する有料顧客の支払い方法入力データ
     * @param inStreetNumber 保存する有料顧客の番地入力データ
     * @param inStreetName 保存する有料顧客の通り名入力データ
     * @param inSuburb 保存する有料顧客の地区入力データ
     * @param inPostCode 保存する有料顧客の郵便番号入力データ
     */
    private void saveExistingPayingCustomer(PayingCustomer customerToSave, String inName, String inEmail, String inPaymentMethod, 
            String inStreetNumber, String inStreetName, String inSuburb, String inPostCode)
    {
        // 保存対象顧客の各フィールドを対応する入力データに設定する
        customerToSave.setName(inName);
        customerToSave.setResidenceAddress(new Address(inStreetNumber, inStreetName, inSuburb, inPostCode));
        customerToSave.setEmailAddress(new EmailAddress(inEmail));
        
        // 支払い方法入力文字列から支払い方法を取得し、保存対象顧客に設定する
        if (inPaymentMethod.equals(CREDIT_CARD_CHOICE))
        {
            customerToSave.setPaymentMethod(PaymentMethod.CreditCard);
        }
        else if (inPaymentMethod.equals(DIRECT_DEBIT_CHOICE))
        {
            customerToSave.setPaymentMethod(PaymentMethod.DirectDebit);
        }
    }
    
    /**
     * 既存の関連顧客の編集データを保存する
     * @param customerToSave 保存する関連顧客
     * @param inName 保存する関連顧客の名前入力データ
     * @param inEmail 保存する関連顧客のメールアドレス入力データ
     * @param inStreetNumber 保存する関連顧客の番地入力データ
     * @param inStreetName 保存する関連顧客の通り名入力データ
     * @param inSuburb 保存する関連顧客の地区入力データ
     * @param inPostCode 保存する関連顧客の郵便番号入力データ
     */
    private void saveExistingAssociateCustomer(AssociateCustomer customerToSave, String inName, String inEmail, 
            String inStreetNumber, String inStreetName, String inSuburb, String inPostCode)
    {
        // 関連顧客の新しい／現在の費用負担元の有料顧客
        PayingCustomer newCoveringCustomer = _coveringCustomerCombo.getValue();
        // この関連顧客の以前の費用負担元の有料顧客
        PayingCustomer oldCoveringCustomer;
        
        // 関連顧客の各データを対応する入力データに設定する
        customerToSave.setName(inName);
        customerToSave.setResidenceAddress(new Address(inStreetNumber, inStreetName, inSuburb, inPostCode));
        customerToSave.setEmailAddress(new EmailAddress(inEmail));
        
        // 関連顧客に費用負担元の有料顧客が設定されていないが、現在費用負担元の有料顧客が存在する場合
        if (newCoveringCustomer == null)
        {
            if (customerToSave.getCoveringCustomer() != null)
            {
                // 現在の費用負担元の有料顧客との関連付けを解除する
                customerToSave.unsubscribeUnderCustomer();
            }
        }
        // 関連顧客に費用負担元の有料顧客が設定されている場合
        else
        {
            // 顧客の以前の費用負担元の有料顧客を取得する
            oldCoveringCustomer = customerToSave.getCoveringCustomer();
            
            // 以前は費用負担元の有料顧客が設定されていなかった場合
            if (oldCoveringCustomer == null)
            {
                // 新しい費用負担元の有料顧客との関連付けを登録する
                customerToSave.subscribeUnderCustomer(newCoveringCustomer);
            }
            // 以前の費用負担元の有料顧客が新しい費用負担元の有料顧客と異なる場合
            else if (oldCoveringCustomer.equals(newCoveringCustomer) == false)
            {
                // 以前の費用負担元の有料顧客との関連付けを解除し、新しい費用負担元の有料顧客との関連付けを登録する
                customerToSave.unsubscribeUnderCustomer();
                customerToSave.subscribeUnderCustomer(newCoveringCustomer);
            }
        }
    }
    
    /**
     * 顧客種別が変更された顧客を保存する
     * @param changedCustomer 種別変更後の保存対象顧客
     * @param inName 顧客の名前入力フィールドデータ
     * @param inEmail 顧客のメールアドレス入力フィールドデータ
     * @param inStreetNumber 顧客の番地入力フィールドデータ
     * @param inStreetName 顧客の通り名入力フィールドデータ
     * @param inSuburb 顧客の地区入力フィールドデータ
     * @param inPostCode 顧客の郵便番号入力フィールドデータ
     * @param newType 顧客の新しい種別入力フィールドデータ
     * @param magazineService 顧客を保存する雑誌サービス
     */
    private void saveExistingChangedCustomerType(Customer changedCustomer, String inName, String inEmail, 
            String inStreetNumber, String inStreetName, String inSuburb, String inPostCode, 
            String newType, ServiceManager magazineService)
    {
        
        // 空のサプリメントリスト
        ArrayList<Supplement> noSupplements = new ArrayList<>();
        
        // 種別が関連顧客に変更された場合の費用負担元の有料顧客およびその名前
        Customer coveringCustomer;
        String coveringCustomerName;
        // 種別が有料顧客に変更された場合の支払い方法入力データ
        String paymentMethodData;
        
        // 顧客が有料顧客に変更された場合
        if (newType.equals(PAYING_CUSTOMER_CHOICE))
        {
            // 以前の顧客をサービスから削除する
            magazineService.removeAssociateCustomer(changedCustomer.getName());
            paymentMethodData = _paymentMethodChoiceBox.getValue();
            
            // 有料顧客を雑誌サービスに追加する
            magazineService.addPayingCustomer(inName, inStreetNumber, inStreetName, inSuburb, inPostCode, inEmail, paymentMethodData, noSupplements);
        }
        // 顧客が関連顧客に変更された場合
        else if (newType.equals(ASSOCIATE_CUSTOMER_CHOICE))
        {
            // 関連顧客の費用負担元の有料顧客入力を取得する
            coveringCustomer = _coveringCustomerCombo.getValue();
            
            // 費用負担元の有料顧客が設定されていない場合は警告を表示して処理を終了する
            if (coveringCustomer == null)
            {
                showWarning("No Covering Customer", "Please select a covering customer for this associate");
                return;
            }
            
            // 費用負担元の有料顧客の名前を取得する
            coveringCustomerName = coveringCustomer.getName();
            
            // 雑誌からこの顧客の有料顧客としての登録を削除する
            magazineService.removePayingCustomer(changedCustomer.getName());
            // 雑誌にこの顧客の関連顧客としての登録を追加する
            magazineService.addAssociateCustomer(inName, inStreetNumber, inStreetName, inSuburb, inPostCode, inEmail, noSupplements, coveringCustomerName);
        }
    }
        
    /**
     * 有料顧客と関連顧客で共通する顧客フォームの項目をクリアする
     */
    private void clearCustomerForm()
    {
        _nameField.clear();
        _emailField.clear();
        _streetNumberField.clear();
        _streetNameField.clear();
        _suburbField.clear();
        _postcodeField.clear();
    }
    
    /**
     * 指定した顧客の情報を編集 UI に読み込む
     * @param customerToLoad 読み込む顧客
     */
    private void loadCustomer(Customer customerToLoad)
    {
        // 読み込み対象の顧客が null の場合は処理を終了する
        if (customerToLoad == null)
        {
            return;
        }
        
        // 読み込み対象顧客の情報を取得し、対応する UI 要素に設定する
        Address customerAddress = customerToLoad.getResidenceAddress();

        _nameField.setText(customerToLoad.getName());
        _emailField.setText(customerToLoad.getEmailAddress());
                
        _streetNumberField.setText(customerAddress.getStreetNumber());
        _streetNameField.setText(customerAddress.getStreetName());
        _suburbField.setText(customerAddress.getSuburb());
        _postcodeField.setText(customerAddress.getPostCode());
        
        // 有料顧客の場合の支払い方法
        PaymentMethod payMethod;
        
        // 読み込み対象が有料顧客の場合
        if (customerToLoad instanceof PayingCustomer)
        {
            // 支払い方法を取得する
            payMethod = ((PayingCustomer) customerToLoad).getPaymentMethod();
            
            // 支払い方法ChoiceBoxを有効化し、顧客の支払い方法を設定する
            _paymentMethodChoiceBox.setDisable(false);
            _paymentMethodChoiceBox.setValue(payMethod.getPaymentMethodName());
            
            // 顧客種別ChoiceBoxを有料顧客に設定する
            _typeChoiceBox.setValue(PAYING_CUSTOMER_CHOICE);
            
            // 費用負担元の有料顧客コンボボックス UI を無効化してクリアする
            _coveringCustomerCombo.setDisable(true);
            _coveringCustomerCombo.setValue(null);
        }
        // 読み込み対象が関連顧客の場合
        else if (customerToLoad instanceof AssociateCustomer)
        {
            // 顧客種別ChoiceBoxを関連顧客に設定する
            _typeChoiceBox.setValue(ASSOCIATE_CUSTOMER_CHOICE);
            
            // 費用負担元の有料顧客コンボボックス UI を有効化する
            _coveringCustomerCombo.setDisable(false);
            _coveringCustomerCombo.setValue(((AssociateCustomer) customerToLoad).getCoveringCustomer());
        }
    }
}
