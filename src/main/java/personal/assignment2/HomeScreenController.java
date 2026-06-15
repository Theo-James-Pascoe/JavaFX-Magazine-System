/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: ホーム画面のコントローラ（HomeScreenController）
 */

package personal.assignment2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

import personal.assignment2.model.ServiceManager;

/**
 * ホーム画面ビュー／FXML 用のコントローラクラス
 * @author パスコー・セオ・ジェームズ
 */
public class HomeScreenController 
{
    /**
     * 作成画面オプションのコールバックメソッド
     * @throws IOException 作成画面を読み込めない場合
     */
    @FXML 
    private void onCreate() throws IOException 
    {
        App.setRoot("CreateScreen");
    }
    
    /**
     * 編集画面オプションのコールバックメソッド
     * @throws IOException 編集画面を読み込めない場合
     */
    @FXML
    private void onEdit() throws IOException 
    {
        App.setRoot("EditScreen");
    }
    
    /**
     * 表示画面オプションのコールバックメソッド
     * @throws IOException 表示画面を読み込めない場合
     */
    @FXML
    private void onView() throws IOException 
    {
        App.setRoot("ViewScreen");
    }
    
    /**
     * デフォルト値読み込みオプションのコールバックメソッド
     */
    @FXML
    private void onLoadDefault()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        // サービスをデフォルト値を持つ新しいオブジェクトに設定する
        App.setMagazineService(new ServiceManager());
        
        alert.setTitle("Magazine Reset");
        alert.setHeaderText(null);
        alert.setContentText("The magazine has been reset to default demonstration values.");
        alert.showAndWait();
    }
}
