/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: アプリケーションのエントリーポイント（App）
 */

package personal.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import personal.assignment2.model.ServiceManager;
import personal.assignment2.model.SerializationManager;

/**
 * アプリケーションの JavaFX エントリーポイントクラス
 * @author パスコー・セオ・ジェームズ
 */
public class App extends Application 
{
    private static Scene _scene;
    // UI が現在表示している雑誌サービス
    private static ServiceManager _magazineService;
    
    // 注: main() 内の launch() 呼び出しは、最終的にこのメソッドを呼び出す
    
    /**
     * アプリケーションの初期化処理を行う JavaFX の start メソッド
     * @param stage アプリケーションのメインステージ
     * @throws IOException シーンの読み込みに問題がある場合
     */
    @Override
    public void start(Stage stage) throws IOException 
    {
        // 保存済みのサービス状態をデシリアライズして読み込む
        _magazineService = SerializationManager.loadServiceState();
        
        // ホーム画面のシーンを作成する
        _scene = new Scene(loadFXML("HomeScreen"), 800, 600);
        // シーンをステージに設定する
        stage.setScene(_scene);
        // ステージを表示する
        stage.show();
    }
    
    /**
     * アプリケーション終了時にサービス状態をシリアライズして保存する JavaFX メソッド
     */
    @Override
    public void stop()
    {
        SerializationManager.saveServiceState(_magazineService);
    }
    
    /**
     * 画面遷移を行うメソッド。現在のシーンのルート FXML を差し替える
     * @param fxml 読み込む FXML
     * @throws IOException FXML が見つからない場合
     */
    static void setRoot(String fxml) throws IOException 
    {
        _scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * FXML ファイルを読み込む
     * @param fxml 読み込む FXML
     * @return 読み込んだ FXML のルートノード
     * @throws IOException FXML が見つからない／読み込めない場合
     */
    private static Parent loadFXML(String fxml) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        
        return root;
    }
    
    /**
     * プログラムのエントリーポイント
     * @param args main() メソッドの標準的なプログラム引数
     */
    public static void main(String[] args) 
    {
        launch();
    }
    
    /**
     * 雑誌サービスの getter メソッド
     * @return 現在の雑誌サービス
     */
    public static ServiceManager getMagazineService()
    {
        return _magazineService;
    }
    
    /**
     * 雑誌サービスの setter メソッド
     * @param inMagazineService 設定する雑誌サービス
     */
    public static void setMagazineService(ServiceManager inMagazineService)
    {
        _magazineService = inMagazineService;
    }
}
