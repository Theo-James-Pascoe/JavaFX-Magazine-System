/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 支払い方法の列挙型（PaymentMethod）
 */

package personal.assignment2.model;

import java.io.Serializable;

/**
 * サービス環境内の有料顧客の支払い方法を表すシンプルな列挙型
 * @author パスコー・セオ・ジェームズ
 */
public enum PaymentMethod implements Serializable
{
    // 利用可能な 2 種類の支払い方法とその文字列値／対応表現
    CreditCard("Credit Card"),
    DirectDebit("Direct Debit");
    
    // 支払い方法名を表す文字列
    private final String _paymentMethodName;
    
    /**
     * 支払い方法種別のコンストラクタ
     * @param inPaymentMethodName 支払い方法名（初期化用）
     */
    PaymentMethod(String inPaymentMethodName)
    {
        _paymentMethodName = inPaymentMethodName;
    }
    
    /**
     * 支払い方法名フィールドの getter メソッド
     * @return 支払い方法名を表す文字列
     */
    public String getPaymentMethodName()
    {
        return _paymentMethodName;
    }
    
    /**
     * 表示名に基づいて支払い方法を取得するメソッド
     * @param nameToRetrieveFrom 対応する支払い方法を取得する表示名
     * @return 表示名に対応する支払い方法
     * @throws IllegalArgumentException 表示名がいずれの支払い方法にも対応しない場合
     */
    public static PaymentMethod retrieveFromDisplayName(String nameToRetrieveFrom)
    {
        // すべての支払い方法を走査し、表示名と一致するものを返す
        for (PaymentMethod currentMethod : PaymentMethod.values())
        {
            if (currentMethod.getPaymentMethodName().equalsIgnoreCase(nameToRetrieveFrom))
            {
                return currentMethod;
            }
        }
        
        // 対応する支払い方法が見つからなかった場合は例外をスローする
        throw new IllegalArgumentException("Display name " + nameToRetrieveFrom + " does not correspond to any payment method");
    }
}
