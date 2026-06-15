/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 表示画面のコントローラ（ViewScreenController）
 */

package personal.assignment2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.io.IOException;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.Supplement;
import personal.assignment2.model.Customer;
import personal.assignment2.model.PayingCustomer;

/**
 * 表示画面ビュー／FXML 用のコントローラクラス
 * @author パスコー・セオ・ジェームズ
 */
public class ViewScreenController 
{    
    // 画面上部の画面切替ボタン
    @FXML
    private ToggleGroup modeToggleGroup;

    // 画面左側のリスト
    
    @FXML
    private ListView<Supplement> supplementListView;
    
    @FXML
    private ListView<Customer> customerListView;

    // 選択されたオブジェクトの情報を表示する右側パネル
    @FXML private VBox infoPanel;

    /**
     * 画面読み込み時に呼び出される JavaFX の initialize メソッド
     */
    @FXML
    private void initialize() 
    {
        populateLists(App.getMagazineService());
        setupEvents();
    }
    
    /**
     * 左側のリストにサプリメントと顧客を設定するメソッド
     * @param magazineService 読み込み元のサービス
     */
    private void populateLists(ServiceManager magazineService)
    {
        ObservableList<Supplement> supplements = FXCollections.observableArrayList(magazineService.retrieveSupplements());
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        customers.addAll(magazineService.retrievePayingCustomers());
        customers.addAll(magazineService.retrieveAssociateCustomers());
        
        supplementListView.setItems(supplements);
        customerListView.setItems(customers);
    }
    
    /**
     * 画面のイベントを設定するメソッド
     */
    private void setupEvents()
    {
        supplementListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            showSupplementDetails(newVal);
        });

        customerListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            showCustomerDetails(newVal);
        });
    }
    
    /**
     * 作成画面へのナビゲーションボタンのコールバックメソッド
     * @throws IOException 作成画面を読み込めない場合
     */
    @FXML private void onCreate() throws IOException 
    {
        App.setRoot("CreateScreen");
    }
    
    /**
     * 編集画面へのナビゲーションボタンのコールバックメソッド
     * @throws IOException 編集画面を読み込めない場合
     */
    @FXML private void onEdit() throws IOException 
    {
        App.setRoot("EditScreen");
    }

    /**
     * 選択されたサプリメントの詳細を情報パネルに表示するメソッド
     * @param supplementToDisplayFor 詳細を表示するサプリメント
     */
    private void showSupplementDetails(Supplement supplementToDisplayFor) 
    {
        // 情報パネルをクリアする
        infoPanel.getChildren().clear();
        
        // 表示対象のサプリメントが正しく渡された場合
        if (supplementToDisplayFor != null) 
        {
            // サプリメントの詳細をラベルとして情報パネルに追加する
            infoPanel.getChildren().addAll(
                new Label("Supplement Name: " + supplementToDisplayFor.getName()),
                new Label("Weekly Cost: " + supplementToDisplayFor.getWeeklyCost())
            );
        }
    }
    
    /**
     * 選択された顧客の詳細を情報パネルに表示するメソッド
     * @param customerToDisplayFor 詳細を表示する顧客
     */
    private void showCustomerDetails(Customer customerToDisplayFor) 
    {
        // 情報パネルをクリアする
        infoPanel.getChildren().clear();
        
        // 表示対象の顧客が正しく渡された場合
        if (customerToDisplayFor != null) 
        {
            // 顧客の詳細をラベルとして情報パネルに追加する
            infoPanel.getChildren().add(new Label("Name: " + customerToDisplayFor.getName()));
            infoPanel.getChildren().add(new Label("Email: " + customerToDisplayFor.getEmailAddress()));
            infoPanel.getChildren().add(new Label("Address: " + customerToDisplayFor.getResidenceAddressAsString()));
            
            // 表示対象が有料顧客の場合、支払い方法を表示するラベルを追加する
            if (customerToDisplayFor instanceof PayingCustomer) 
            {
                PayingCustomer payer = (PayingCustomer) customerToDisplayFor;
                infoPanel.getChildren().add(new Label("Payment Method: " + payer.getPaymentMethod().getPaymentMethodName()));
            }
            
            // 顧客の購読サプリメントを表示する
            infoPanel.getChildren().add(new Label("Subscribed Supplements:"));
            
            for (Supplement currentSupplement : customerToDisplayFor.getSubscribedSupplements())
            {
                infoPanel.getChildren().add(new Label("- " + currentSupplement.getName()));
            }
        }
    }
}
