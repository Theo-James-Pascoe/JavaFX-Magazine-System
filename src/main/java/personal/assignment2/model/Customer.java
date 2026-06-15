/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 顧客の抽象基底クラス（Customer）
 */

package personal.assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * サービス環境内の顧客の抽象基底クラス。
 * 有料顧客と関連顧客の双方に必要なすべての要素を含む
 * @author パスコー・セオ・ジェームズ
 */
public abstract class Customer implements Serializable
{
    // 顧客の名前
    protected String _name = "Name not initialized";
    // 顧客のメールアドレス
    protected Address _residenceAddress;
    protected EmailAddress _emailAddress;
    // 顧客が購読するサプリメントのリスト
    protected ArrayList<Supplement> _subscribedSupplements = new ArrayList<>();
    
    /**
     * Customer クラスのコンストラクタ
     * @param inName 顧客の名前に設定する名前
     * @param inResidenceAddress 顧客が居住している住所
     * @param inEmailAddress 顧客のメールアドレスに設定するメールアドレス
     */
    public Customer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress)
    {
        _name = inName;
        _residenceAddress = inResidenceAddress;
        _emailAddress = inEmailAddress;
    }
    
    /**
     * サプリメントリストを指定して Customer を生成するコンストラクタ
     * @param inName 顧客の名前に設定する名前
     * @param inResidenceAddress 顧客が居住している住所
     * @param inEmailAddress 顧客のメールアドレスに設定するメールアドレス
     * @param inSupplements 顧客が購読するサプリメントのリスト
     */
    public Customer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress, ArrayList<Supplement> inSupplements)
    {
        _name = inName;
        _residenceAddress = inResidenceAddress;
        _emailAddress = inEmailAddress;
        _subscribedSupplements = inSupplements;
    }
    
    /**
     * 一覧表示用に toString() をオーバーライド
     * @return 顧客の名前
     */
    @Override
    public String toString()
    {
        return getName();
    }
    
    /**
     * 名前フィールドの getter メソッド
     * @return 顧客の名前を表す文字列
     */
    public String getName()
    {
        return _name;
    }
    
    /**
     * 名前フィールドの setter メソッド
     * @param inName 設定する名前
     */
    public void setName(String inName)
    {
        _name = inName;
    }
    
    /**
     * 居住住所を Address オブジェクトとして取得する getter メソッド
     * @return Address オブジェクトとしての居住住所
     */
    public Address getResidenceAddress()
    {
        return _residenceAddress;
    }
    
    /**
     * 居住住所を文字列として取得する getter メソッド
     * @return 文字列としての居住住所
     */
    public String getResidenceAddressAsString()
    {
        return _residenceAddress.getFullAddress();
    }
    
    /**
     * 居住住所の setter メソッド
     * @param inAddress 設定する住所
     */
    public void setResidenceAddress(Address inAddress)
    {
        _residenceAddress = inAddress;
    }
    
    /**
     * メールアドレスフィールドの getter メソッド
     * @return 顧客のメールアドレスを表す文字列
     */
    public String getEmailAddress()
    {
        return _emailAddress.getEmail();
    }
    
    /**
     * メールアドレスフィールドの setter メソッド
     * @param inEmail 設定するメールアドレス
     */
    public void setEmailAddress(EmailAddress inEmail)
    {
        _emailAddress = inEmail;
    }
    
    /**
     * 購読サプリメントフィールドの getter メソッド
     * @return 顧客が購読するサプリメントを含む Supplement の ArrayList
     */
    public ArrayList<Supplement> getSubscribedSupplements()
    {
        return _subscribedSupplements;
    }
    
    /**
     * 購読サプリメントフィールドの setter メソッド
     * @param inSupplements 設定する購読サプリメント
     */
    public void setSubscribedSupplements(ArrayList<Supplement> inSupplements)
    {
        _subscribedSupplements = inSupplements;
    }
}
