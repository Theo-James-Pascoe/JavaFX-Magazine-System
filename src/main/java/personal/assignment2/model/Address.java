/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: 住所のドメインモデル（Address）
 */

package personal.assignment2.model;

import java.io.Serializable;

/**
 * サービス環境内の住所を表す
 * @author パスコー・セオ・ジェームズ
 */
public class Address implements Serializable
{
    private String _streetNumber;
    private String _streetName;
    private String _suburb;
    // すべての国で郵便番号が数字のみとは限らないため、_postcode は String 型としている
    private String _postcode;
    
    /**
     * Address クラスのコンストラクタ
     * @param inStreetNumber 住所の番地
     * @param inStreetName 住所の通り名
     * @param inSuburb 住所の地区
     * @param inPostcode 住所の郵便番号
     */    
    public Address(String inStreetNumber, String inStreetName, String inSuburb , String inPostcode)
    {
        _streetNumber = inStreetNumber;
        _streetName = inStreetName;
        _suburb = inSuburb;
        _postcode = inPostcode;
    }
    
    /**
     * 完全な住所を文字列として取得する getter メソッド
     * @return 文字列としての完全な住所
     */
    public String getFullAddress()
    {
        return _streetNumber + ", " + _streetName + ", " + _suburb + ", " + _postcode;
    }
    
    /**
     * 番地の getter メソッド
     * @return 番地
     */
    public String getStreetNumber()
    {
        return _streetNumber;
    }
    
    /**
     * 番地の setter メソッド
     * @param inStreetNumber 設定する番地
     */
    public void setStreetNumber(String inStreetNumber)
    {
        _streetNumber = inStreetNumber;
    }
    
    /**
     * 通り名の getter メソッド
     * @return 通り名
     */
    public String getStreetName()
    {
        return _streetName;
    }
    
    /**
     * 通り名の setter メソッド
     * @param inStreetName 設定する通り名
     */
    public void setStreetName(String inStreetName)
    {
        _streetName = inStreetName;
    }
    
    /**
     * 地区の getter メソッド
     * @return 地区
     */
    public String getSuburb()
    {
        return _suburb;
    }
    
    /**
     * 地区の setter メソッド
     * @param inSuburb 設定する地区
     */
    public void setSuburb(String inSuburb)
    {
        _suburb = inSuburb;
    }
    
    /**
     * 郵便番号の getter メソッド
     * @return 郵便番号
     */
    public String getPostCode()
    {
        return _postcode;
    }
    
    /**
     * 郵便番号の setter メソッド
     * @param inPostcode 設定する郵便番号
     */
    public void setPostCode(String inPostcode)
    {
        _postcode = inPostcode;
    }
}
