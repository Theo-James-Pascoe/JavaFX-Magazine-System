/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 関連顧客のドメインモデル（AssociateCustomer）
 */

package personal.assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * サービス環境内の関連顧客を表す
 * @author パスコー・セオ・ジェームズ
 */
public class AssociateCustomer extends Customer implements Serializable
{
    // この関連顧客の費用を負担する有料顧客
    private PayingCustomer _coveringCustomer;
    
    /**
     * AssociateCustomer クラスのコンストラクタ
     * @param inName 関連顧客の名前に設定する文字列
     * @param inResidenceAddress 顧客が居住している住所
     * @param inEmailAddress 関連顧客のメールアドレスに設定する文字列
     */
    public AssociateCustomer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress)
    {
        super(inName, inResidenceAddress, inEmailAddress);
    }
    
    /**
     * サプリメントリストを指定して関連顧客を生成するコンストラクタ
     * @param inName 関連顧客の名前に設定する文字列
     * @param inResidenceAddress 顧客が居住している住所
     * @param inEmailAddress 関連顧客のメールアドレスに設定する文字列
     * @param inSupplements この関連顧客が購読するサプリメントのリスト
     */
    public AssociateCustomer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress, ArrayList<Supplement> inSupplements)
    {
        super(inName, inResidenceAddress, inEmailAddress, inSupplements);
    }
    
    /**
     * この関連顧客の費用負担元の有料顧客の getter メソッド
     * @return 費用負担元の有料顧客
     */
    public PayingCustomer getCoveringCustomer()
    {
        return _coveringCustomer;
    }
    
    /**
     * 関連顧客を指定された有料顧客と関連付けて登録するメソッド
     * @param customerToSubscribeUnder この関連顧客の費用を負担する有料顧客
     * @throws IllegalStateException この関連顧客がすでに別の有料顧客の費用負担の対象になっている場合にスローされる
     */
    public void subscribeUnderCustomer(PayingCustomer customerToSubscribeUnder)
    {
        /** この関連顧客がすでにいずれかの有料顧客の費用負担の対象になっている場合は例外をスローする。
           課題仕様では関連顧客が複数の有料顧客に費用負担されるとは述べられておらず、
           代わりに「指定された有料顧客」に負担されると記載されている。
           関連顧客の費用は 1 人の有料顧客のみが負担する方が直感的にも妥当であると判断した
        */
        if (_coveringCustomer != null)
        {
            throw new IllegalStateException(getName() + " is already covered by another customer (" + _coveringCustomer.getName() + ").");
        }
        
        // この関連顧客の費用負担元の有料顧客を設定する
        _coveringCustomer = customerToSubscribeUnder;
        // この関連顧客を費用負担元の有料顧客の関連顧客リストに追加する
        _coveringCustomer.addAssociateCustomer(this);
    }
    
    /**
     * この関連顧客と費用負担元の有料顧客の関連付けを解除するメソッド
     * @throws IllegalStateException 費用負担元の有料顧客が設定されていない場合
     */
    public void unsubscribeUnderCustomer()
    {
        // 費用負担元の有料顧客が設定されていない場合、関連付け解除はできないため、例外をスローする
        if (_coveringCustomer == null)
        {
            throw new IllegalStateException(getName() + " is not covered by any customer.");
        }
        
        // この顧客を費用負担元の有料顧客の関連顧客リストから削除する
        _coveringCustomer.removeAssociateCustomer(this);
        // 費用負担元の有料顧客を null に設定し、費用負担の対象ではなくなったことを示す
        _coveringCustomer = null;
    }
}
