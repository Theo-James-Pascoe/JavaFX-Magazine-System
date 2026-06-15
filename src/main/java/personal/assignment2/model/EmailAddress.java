/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: メールアドレスの値オブジェクト（EmailAddress）
 */

package personal.assignment2.model;

import java.io.Serializable;

// 注:  単一フィールドのラッパーだが、課題 1 のフィードバックを受けて実装している

/**
 * サービス環境内のメールアドレスを表す
 * @author パスコー・セオ・ジェームズ
 */
public class EmailAddress implements Serializable
{
    // 顧客のメールアドレス
    private final String _email;
    
    /**
     * EmailAddress クラスのコンストラクタ
     * @param inEmail メールアドレスに設定するアドレス
     */
    public EmailAddress(String inEmail)
    {
        _email = inEmail;
    }
    
    /**
     * メールアドレスの getter メソッド
     * @return メールアドレス
     */
    public String getEmail()
    {
        return _email;
    }
    
    // 注:  メールアドレスは変更する必要がない。新しい EmailAddress オブジェクトを
    //      作成すればよいため。このクラスには setter メソッドは存在しない
}
