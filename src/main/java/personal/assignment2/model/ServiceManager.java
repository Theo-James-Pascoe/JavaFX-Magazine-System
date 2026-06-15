/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: モデル操作の仲介クラス（ServiceManager）
 */

package personal.assignment2.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * MVC パターンのコントローラから操作しやすいよう、モデルの主要な機能の大半を
 * カプセル化するクラス。このクラスはコントローラとモデルの間の仲介役として
 * 機能し、コントローラがモデルをどのように操作できるかを定める構造を提供する。
 * 
 * 課題 1 のフィードバックでは、顧客の追加や削除などの機能を担う MagazineService クラスの
 * 導入が提案されていた。この提案は課題 2 の構造に合わせて MVC を維持するための
 * 設計に取り入れている。
 * @author パスコー・セオ・ジェームズ
 */
public class ServiceManager implements Serializable
{
    // このサービスが管理する雑誌
    private Magazine _serviceMagazine;
    
    /**
     * ServiceManager クラスのデフォルトコンストラクタ
     */
    public ServiceManager()
    {
        // デモ用の既定値でサービスを作成する
        setToDemonstrationValues();
    }
    
    /**
     * 週額料金を指定して雑誌サービスを生成するコンストラクタ。
     * UI の作成画面で主に使用される。
     * @param inMagazineCost 雑誌の週額料金に設定する金額
     */
    public ServiceManager(int inMagazineCost)
    {
        _serviceMagazine = new Magazine(inMagazineCost);
    }
    
    /**
     * サービス／雑誌をデモ用の既定値に設定するメソッド
     */
    private void setToDemonstrationValues()
    {
        _serviceMagazine = new Magazine(10);
        
        Supplement supplementA = addSupplement("Examplethyne", 5);
        Supplement supplementB = addSupplement("Theoretacine", 10);
        
        ArrayList<Supplement> supplementSetA = new ArrayList<>(Arrays.asList(supplementA));
        ArrayList<Supplement> supplementSetB = new ArrayList<>(Arrays.asList(supplementA, supplementB));
        
        addPayingCustomer("Ali", "1", "Abby Way", "Aville", "1011", "ali@email.com", "Credit Card", supplementSetA);
        addAssociateCustomer("Albert", "2", "Bee Boulevard", "Beville", "2011", "albert@email.com", supplementSetB, "Ali");   
    }
    
    /* 注:  このクラスのメソッドの多くは、コントローラでの利用のためにモデルの機能を
            カプセル化することに重点を置いている。これによりコントローラとモデルの
            操作を整理し、結果として MVC をよりよく維持できる。
    
            そのため、これらのメソッドの多くは Magazine などモデル内の他クラスの機能を
            ラップしている。
    */
    
    /**
     * 雑誌内のすべてのサプリメントを取得する、コントローラ向けのラッパーメソッド
     * @return 雑誌内のすべてのサプリメント
     */
    public ArrayList<Supplement> retrieveSupplements()
    {
        // 雑誌のサプリメントを取得する
        ArrayList<Supplement> retrievedSupplements = _serviceMagazine.getSupplementList();      
       
        // 取得結果が null の場合は空のリストを返す
        if (retrievedSupplements == null)
        {
            return new ArrayList<Supplement>();
        }
        
        // サプリメントが存在する場合は取得したサプリメントを返す
        return retrievedSupplements;
    }
    
    /**
     * 雑誌内のすべての有料顧客を取得する、コントローラ向けのラッパーメソッド
     * @return 雑誌内のすべての有料顧客
     */
    public ArrayList<PayingCustomer> retrievePayingCustomers()
    {
        // 雑誌の有料顧客を取得する
        ArrayList<PayingCustomer> retrievedPayingCustomers = _serviceMagazine.getPayingCustomerList();
        
        // 取得結果が null の場合は空のリストを返す
        if (retrievedPayingCustomers == null)
        {
            return new ArrayList<PayingCustomer>();
        }
        
        // 有料顧客が存在する場合は取得した有料顧客を返す
        return retrievedPayingCustomers;    
    }
    
    /**
     * 雑誌内のすべての関連顧客を取得する、コントローラ向けのラッパーメソッド
     * @return 雑誌内のすべての関連顧客
     */
    public ArrayList<AssociateCustomer> retrieveAssociateCustomers()
    {
        // 雑誌の関連顧客を取得する
        ArrayList<AssociateCustomer> retrievedAssociateCustomers = _serviceMagazine.getAssociateCustomerList();
        
        // 取得結果が null の場合は空のリストを返す
        if (retrievedAssociateCustomers == null)
        {
            return new ArrayList<AssociateCustomer>();
        }
        
        // 関連顧客が存在する場合は取得した関連顧客を返す
        return retrievedAssociateCustomers;    
    }
    
    /**
     * 雑誌にサプリメントを追加する、コントローラ向けのラッパーメソッド
     * @param inName 追加するサプリメントの名前
     * @param inWeeklyCost 追加するサプリメントの週額料金
     * @return 追加されたサプリメント
     */
    public Supplement addSupplement(String inName, int inWeeklyCost)
    {
        Supplement supplementToAdd = new Supplement(inName, inWeeklyCost);
        _serviceMagazine.addSupplement(supplementToAdd);
        
        return supplementToAdd;
    }
    
    /**
     * 名前を指定して雑誌からサプリメントを削除する、コントローラ向けのラッパーメソッド
     * @param supplementToRemoveName 削除するサプリメントの名前
     */
    public void removeSupplement(String supplementToRemoveName)
    {
        // 名前で削除対象のサプリメントを取得する
        Supplement supplementToRemove = _serviceMagazine.getSupplement(supplementToRemoveName);
        // サプリメントを削除する
        _serviceMagazine.removeSupplement(supplementToRemove);
    }
    
    /**
     * 雑誌に有料顧客を追加する、コントローラ向けのラッパーメソッド
     * @param customerName 追加する有料顧客の名前
     * @param inStreetNumber 追加する有料顧客の番地
     * @param inStreetName 追加する有料顧客の通り名
     * @param inSuburb 追加する有料顧客の地区
     * @param inPostCode 追加する有料顧客の郵便番号
     * @param email 追加する有料顧客のメールアドレス
     * @param paymentMethodName 有料顧客の支払い方法名
     * @param interestedSupplements 有料顧客が購読するサプリメント
     * @return 追加された有料顧客
     */
    public PayingCustomer addPayingCustomer(String customerName, String inStreetNumber, String inStreetName, String inSuburb, String inPostCode, String email, String paymentMethodName, ArrayList<Supplement> interestedSupplements)
    {
        Address customerAddress = new Address(inStreetNumber, inStreetName, inSuburb, inPostCode);
        EmailAddress customerEmail = new EmailAddress(email);
        PaymentMethod customerPaymentMethod = PaymentMethod.retrieveFromDisplayName(paymentMethodName);
        
        PayingCustomer payingCustomerToAdd = new PayingCustomer(customerName, customerAddress, customerEmail, customerPaymentMethod, interestedSupplements);
        _serviceMagazine.addPayingCustomer(payingCustomerToAdd);
        
        return payingCustomerToAdd;
    }
    
    /**
     * 雑誌に関連顧客を追加する、コントローラ向けのラッパーメソッド
     * @param customerName 追加する関連顧客の名前
     * @param inStreetNumber 追加する関連顧客の番地
     * @param inStreetName 追加する関連顧客の通り名
     * @param inSuburb 追加する関連顧客の地区
     * @param inPostCode 追加する関連顧客の郵便番号
     * @param email 追加する関連顧客のメールアドレス
     * @param interestedSupplements 関連顧客が購読するサプリメント
     * @param coveringCustomerName 関連顧客の費用を負担する有料顧客の名前
     * @return 追加された関連顧客
     */
    public AssociateCustomer addAssociateCustomer(String customerName, String inStreetNumber, String inStreetName, String inSuburb, String inPostCode, String email, ArrayList<Supplement> interestedSupplements, String coveringCustomerName)
    {
        Address customerAddress = new Address(inStreetNumber, inStreetName, inSuburb, inPostCode);
        EmailAddress customerEmail = new EmailAddress(email);
        PayingCustomer coveringCustomer = _serviceMagazine.getPayingCustomer(coveringCustomerName);
        
        AssociateCustomer associateCustomerToAdd = new AssociateCustomer(customerName, customerAddress, customerEmail, interestedSupplements);
        _serviceMagazine.addAssociateCustomer(associateCustomerToAdd, coveringCustomer);
        
        return associateCustomerToAdd;
    }
    
    /**
     * 雑誌から有料顧客を削除する、コントローラ向けのラッパーメソッド
     * @param customerToRemoveName 削除する有料顧客の名前
     */
    public void removePayingCustomer(String customerToRemoveName)
    {
        PayingCustomer customerToRemove = _serviceMagazine.getPayingCustomer(customerToRemoveName);
        _serviceMagazine.removePayingCustomer(customerToRemove);
    }
    
    /**
     * 雑誌から関連顧客を削除する、コントローラ向けのラッパーメソッド
     * @param customerToRemoveName 削除する関連顧客の名前
     */
    public void removeAssociateCustomer(String customerToRemoveName)
    {
        AssociateCustomer customerToRemove = _serviceMagazine.getAssociateCustomer(customerToRemoveName);
        _serviceMagazine.removeAssociateCustomer(customerToRemove);
    }
}
