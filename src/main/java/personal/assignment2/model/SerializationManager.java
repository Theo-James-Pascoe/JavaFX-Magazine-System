/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: データのシリアライズ管理（SerializationManager）
 */

package personal.assignment2.model;

import java.io.*;

/**
 * プログラム内のすべてのシリアライズ処理を担当するクラス
 * @author パスコー・セオ・ジェームズ
 */
public class SerializationManager 
{
    // ServiceManager オブジェクトを保存するファイル名
    private static final String SAVE_FILENAME = "savedData.ser";
    
    /**
     * サービスの状態を保存するメソッド
     * @param serviceToSave シリアライズ対象の ServiceManager
     */
    public static void saveServiceState(ServiceManager serviceToSave)
    {
        // オブジェクトのシリアライズを試み、発生した IOException を捕捉して処理する
        try
        {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILENAME));
            outputStream.writeObject(serviceToSave);
        }
        catch (IOException exception)
        {
            exception.getMessage();
        }
    }
    
    /**
     * 保存済みのサービス状態を読み込むメソッド
     * @return デシリアライズして読み込んだサービス状態
     */
    public static ServiceManager loadServiceState()
    {
        // 指定されたファイル名に保存されたサービス状態の読み込みと返却を試みる。
        // この処理で発生する IOException を捕捉して処理する。
        try
        {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILENAME));
            System.out.println("Magazine state successfully loaded");
            return (ServiceManager) inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException exception)
        {
            System.err.println("An error occurred when loading the Magazine state: " + exception.getMessage());
            return new ServiceManager();
        }
    }
}
