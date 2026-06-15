/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 雑誌および購読者管理のドメインモデル（Magazine）
 */

package personal.assignment2.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * サービス環境内の雑誌を表す
 * @author パスコー・セオ・ジェームズ
 */
public class Magazine implements Serializable
{
    // 雑誌の週額料金
    private int _weeklyCost;
    // この雑誌で利用可能なサプリメントのリスト
    private ArrayList<Supplement> _supplements = new ArrayList<>();
    // この雑誌を購読している有料顧客のリスト
    private ArrayList<PayingCustomer> _subscribedPayingCustomers = new ArrayList<>();
    // この雑誌を購読している関連顧客のリスト
    private ArrayList<AssociateCustomer> _subscribedAssociateCustomers = new ArrayList<>();
    
    /**
     * Magazine クラスのコンストラクタ
     * @param inWeeklyCost 雑誌の週額料金
     */
    public Magazine(int inWeeklyCost)
    {
        _weeklyCost = inWeeklyCost;
    }
    
    /**
     * サプリメントリストを指定して雑誌を生成するコンストラクタ
     * @param inWeeklyCost 雑誌の週額料金
     * @param inSupplements 雑誌で利用可能なサプリメントのリスト
     */
    public Magazine(int inWeeklyCost, ArrayList<Supplement> inSupplements)
    {
        _weeklyCost = inWeeklyCost;
        _supplements = inSupplements;
    }
    
    /**
     * 週額料金フィールドの getter メソッド
     * @return 整数としての週額料金
     */
    public int getWeeklyCost()
    {
        return _weeklyCost;
    }
    
    /**
     * サプリメントリスト全体の getter メソッド
     * @return サプリメントのリスト
     */
    public ArrayList<Supplement> getSupplementList()
    {
        return _supplements;
    }
    
    /**
     * 有料顧客リスト全体の getter メソッド
     * @return 有料顧客のリスト
     */    
    public ArrayList<PayingCustomer> getPayingCustomerList()
    {
        return _subscribedPayingCustomers;
    }
    
    /**
     * 関連顧客リスト全体の getter メソッド
     * @return 関連顧客のリスト
     */    
    public ArrayList<AssociateCustomer> getAssociateCustomerList()
    {
        return _subscribedAssociateCustomers;
    }
   
    /**
     * 名前を指定して有料顧客を取得する
     * @param customerNameToFind 検索する有料顧客の名前
     * @return 見つかった有料顧客
     * @throws IllegalArgumentException 雑誌内に有料顧客が見つからなかった場合にスローされる
     */        
    public PayingCustomer getPayingCustomer(String customerNameToFind)
    {
        for (PayingCustomer currentCustomer : _subscribedPayingCustomers)
        {
            if (currentCustomer.getName() == customerNameToFind)
            {
                return currentCustomer;
            }
        }
        
        // 一致する顧客が見つからなかった場合、例外をスローする
        throw new IllegalArgumentException("Customer " + customerNameToFind + " could not be found in the magazine");
    }
    
    /**
     * 名前を指定して関連顧客を取得する
     * @param customerNameToFind 検索する関連顧客の名前
     * @return 見つかった関連顧客
     * @throws IllegalArgumentException 雑誌内に関連顧客が見つからなかった場合にスローされる
     */      
    public AssociateCustomer getAssociateCustomer(String customerNameToFind)
    {
        for (AssociateCustomer currentCustomer : _subscribedAssociateCustomers)
        {
            if (currentCustomer.getName() == customerNameToFind)
            {
                return currentCustomer;
            }
        }
        
        // 一致する顧客が見つからなかった場合、例外をスローする
        throw new IllegalArgumentException("Customer " + customerNameToFind + " could not be found in the magazine");
    }
    
    /**
     * サプリメントの名前を指定して雑誌からサプリメントを取得する
     * @param supplementToFind 検索するサプリメントの名前
     * @return 指定された名前に一致するサプリメント
     * @throws IllegalArgumentException 雑誌内にサプリメントが見つからない場合にスローされる
     */
    public Supplement getSupplement(String supplementToFind)
    {
        // 各サプリメントを走査し、検索対象の名前と一致するサプリメントを返す
        for (Supplement currentSupplement : _supplements)
        {
            if (currentSupplement.getName() == supplementToFind)
            {
                return currentSupplement;
            }
        }
        
        // 一致するサプリメントが見つからなかった場合、例外をスローする
        throw new IllegalArgumentException("Supplement " + supplementToFind + " could not be found in the magazine");
    }
    
    /**
     * 雑誌にサプリメントを追加する
     * @param supplementToAdd 雑誌に追加するサプリメント
     * @throws IllegalStateException サプリメントがすでに雑誌に含まれている場合にスローされる
     */    
    public void addSupplement(Supplement supplementToAdd)
    {
        // サプリメントの重複登録を防ぐため、重複するサプリメントが雑誌内に存在しないようにする
        if (_supplements.contains(supplementToAdd) == true)
        {
            throw new IllegalStateException("Supplement " + supplementToAdd.getName() + " is already within the magazine");
        }
        
        // 重複でなければサプリメントを雑誌に追加する
        _supplements.add(supplementToAdd);
    }
    
        
    /**
     * 雑誌からサプリメントを削除する
     * @param supplementToRemove 雑誌から削除するサプリメント
     * @throws IllegalStateException サプリメントが雑誌に含まれていない場合にスローされる
     */  
    public void removeSupplement(Supplement supplementToRemove)
    {
        if (_supplements.contains(supplementToRemove) == false)
        {
            throw new IllegalStateException("Supplement " + supplementToRemove.getName() + " was not in the magazine");
        }
        
        _supplements.remove(supplementToRemove);
    }
    
    /**
     * 有料顧客を雑誌の購読有料顧客リストに追加する
     * @param customerToAdd 追加する有料顧客
     * @throws IllegalStateException 顧客がすでにこの雑誌を購読している場合にスローされる
     */
    public void addPayingCustomer(PayingCustomer customerToAdd)
    {
        // 同一顧客の重複登録を防ぐため、追加する顧客がすでに購読している場合は例外をスローする
        if (_subscribedPayingCustomers.contains(customerToAdd) == true)
        {
            throw new IllegalStateException("Customer " + customerToAdd.getName() + " is already subscribed.");
        }
                
        // 指定された顧客をこの雑誌の購読顧客リストに追加する
        _subscribedPayingCustomers.add(customerToAdd);
    }
    
    /**
     * 有料顧客を雑誌の購読有料顧客リストから削除する
     * @param customerToRemove 削除する有料顧客
     * @throws IllegalArgumentException 顧客がこの雑誌を購読していない場合にスローされる
     */
    public void removePayingCustomer(PayingCustomer customerToRemove)
    {
        // 削除する顧客がこの雑誌を購読していない場合、削除もできないため
        // 例外をスローする
        if (_subscribedPayingCustomers.contains(customerToRemove) == false)
        {
            throw new IllegalArgumentException("Customer " + customerToRemove.getName() + " was not subscribed");
        }
        
        // 有料顧客を削除すると関連顧客の負担関係が失われるため、その関連顧客もすべて削除する
        removeGroupOfAssociates(customerToRemove.getAssociatedCustomers());
        
        // 指定された顧客をこの雑誌の購読顧客リストから削除する
        _subscribedPayingCustomers.remove(customerToRemove);
    }
    
    /**
     * 関連顧客を雑誌の購読関連顧客リストに追加する
     * @param customerToAdd 追加する関連顧客
     * @param customerToSubscribeUnder この関連顧客の費用を負担する有料顧客
     * @throws IllegalStateException 顧客がすでにこの雑誌を購読している場合にスローされる
     */
    public void addAssociateCustomer(AssociateCustomer customerToAdd, PayingCustomer customerToSubscribeUnder)
    {        
        // 同一顧客の重複登録を防ぐため、追加する顧客がすでに購読している場合は例外をスローする
        if (_subscribedAssociateCustomers.contains(customerToAdd) == true)
        {
            throw new IllegalStateException("Customer " + customerToAdd.getName() + " is already subscribed.");
        }
        
        if (_subscribedPayingCustomers.contains(customerToSubscribeUnder) == false)
        {
            throw new IllegalStateException("Covering customer  " + customerToSubscribeUnder.getName() + " is not subscribed to the magazine and therefore cannot cover.");
        }
        
        // 関連顧客と費用負担元の有料顧客の関連付けを行う
        customerToAdd.subscribeUnderCustomer(customerToSubscribeUnder);
        
        // 指定された顧客をこの雑誌の購読顧客リストに追加する
        _subscribedAssociateCustomers.add(customerToAdd);
    }
    
    /**
     * 関連顧客を雑誌の購読関連顧客リストから削除する
     * @param customerToRemove 削除する関連顧客
     * @throws IllegalArgumentException 顧客がこの雑誌を購読していない場合にスローされる
     */
    public void removeAssociateCustomer(AssociateCustomer customerToRemove)
    {
        // 削除する顧客がこの雑誌を購読していない場合、削除もできないため
        // 例外をスローする
        if (_subscribedAssociateCustomers.contains(customerToRemove) == false)
        {
            throw new IllegalArgumentException("Customer " + customerToRemove.getName() + " was not subscribed");
        }
        
        customerToRemove.unsubscribeUnderCustomer();
        
        // 指定された顧客をこの雑誌の購読顧客リストから削除する
        _subscribedAssociateCustomers.remove(customerToRemove);
    }
    
    /**
     * 関連顧客のグループを雑誌の購読から解除する。雑誌から有料顧客を削除する際に呼び出される
     * @param associatesToRemove 削除する関連顧客のリスト
     */
    private void removeGroupOfAssociates(ArrayList<AssociateCustomer> associatesToRemove)
    {
        if (associatesToRemove.isEmpty() == true)
        {
            return;
        }
        
        // _subscribedAssociateCustomers のコピーを走査するため、
        // 走査中のリストと変更対象のリストが同一にならない
        for (AssociateCustomer currentCustomer : new ArrayList<>(_subscribedAssociateCustomers))
        {
            removeAssociateCustomer(currentCustomer);
        }
    }
}
