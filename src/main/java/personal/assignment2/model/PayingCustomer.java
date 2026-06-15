/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 有料顧客のドメインモデル（PayingCustomer）
 */

package personal.assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * サービス環境内の有料顧客を表す
 * @author パスコー・セオ・ジェームズ
 */
public class PayingCustomer extends Customer implements Serializable
{
    // 顧客の支払い方法
    private PaymentMethod _paymentMethod = PaymentMethod.CreditCard;
    // この有料顧客が費用を負担する関連顧客のリスト
    private ArrayList<AssociateCustomer> _associateCustomers = new ArrayList<>();
    
     /**
     * PayingCustomer クラスのコンストラクタ
     * @param inName 顧客の名前に設定する文字列
     * @param inEmailAddress 顧客のメールアドレスに設定する文字列
     * @param inPaymentMethod 顧客の支払い方法に設定する PaymentMethod
     * @param inSupplements 顧客が購読するサプリメントのリスト
     */
    public PayingCustomer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress, PaymentMethod inPaymentMethod, ArrayList<Supplement> inSupplements)
    {
        super(inName, inResidenceAddress, inEmailAddress, inSupplements);
        
        _paymentMethod = inPaymentMethod;
    }
    
    /**
     * サプリメントリストなしで有料顧客を生成するコンストラクタ
     * @param inName 顧客の名前に設定する文字列
     * @param inResidenceAddress 顧客の住所に設定する Address
     * @param inEmailAddress 顧客のメールアドレスに設定する文字列
     * @param inPaymentMethod 顧客の支払い方法に設定する PaymentMethod
     */
    public PayingCustomer(String inName, Address inResidenceAddress, EmailAddress inEmailAddress, PaymentMethod inPaymentMethod)
    {
        super(inName, inResidenceAddress, inEmailAddress);
        
        _paymentMethod = inPaymentMethod;
    }
    
    /**
     * 支払い方法フィールドの getter メソッド
     * @return 有料顧客の支払い方法を表す PaymentMethod
     */
    public PaymentMethod getPaymentMethod()
    {
        return _paymentMethod;
    }
    
    /**
     * 支払い方法フィールドの setter メソッド
     * @param inPaymentMethod 設定する支払い方法
     */
    public void setPaymentMethod(PaymentMethod inPaymentMethod)
    {
        _paymentMethod = inPaymentMethod;
    }
    
    /**
     * 関連顧客フィールドの getter メソッド
     * @return 有料顧客の関連顧客を含む ArrayList
     */
    public ArrayList<AssociateCustomer> getAssociatedCustomers()
    {
        return _associateCustomers;
    }
    
    /**
     * 有料顧客に関連顧客の費用負担を関連付けるメソッド
     * @param customerToAdd 有料顧客が費用を負担する関連顧客
     * @throws IllegalStateException すでにその関連顧客を費用負担の対象としている場合にスローされる
     */
    protected void addAssociateCustomer(AssociateCustomer customerToAdd)
    {
        // 追加する関連顧客がすでにこの有料顧客の費用負担の対象になっている場合は例外をスローする
        if (_associateCustomers.contains(customerToAdd) == true)
        {
            throw new IllegalStateException("Customer " + customerToAdd.getName() + " is already associated with " + getName());
        }
                
        // 関連顧客をこの有料顧客の関連顧客リストに追加する
        _associateCustomers.add(customerToAdd);
    }
    
    /**
     * 有料顧客と特定の関連顧客の費用負担の関連付けを解除するメソッド
     * @param customerToRemove 費用負担の関連付けを解除する関連顧客
     * @throws IllegalArgumentException 指定された関連顧客を負担していない場合にスローされる
     */
    protected void removeAssociateCustomer(AssociateCustomer customerToRemove)
    {
        // 指定された関連顧客がこの有料顧客の費用負担の対象ではない場合は例外をスローする
        if (_associateCustomers.contains(customerToRemove) == false)
        {
            throw new IllegalArgumentException(customerToRemove.getName() + " is not associated with " + getName());
        }
        
        // 指定された関連顧客をこの有料顧客の関連顧客リストから削除する
        _associateCustomers.remove(customerToRemove);
    }
}
