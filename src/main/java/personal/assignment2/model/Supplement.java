/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: サプリメントのドメインモデル（Supplement）
 */

package personal.assignment2.model;

import java.io.Serializable;

/**
 * サービス環境内のサプリメントを表す
 * @author パスコー・セオ・ジェームズ
 */
public class Supplement implements Serializable
{
    private String _name;
    private int _weeklyCost;
    
    /**
     * Supplement クラスのコンストラクタ
     * @param inName サプリメントの名前（初期化用）
     * @param inWeeklyCost 週額料金（初期化用）
     */
    public Supplement(String inName, int inWeeklyCost)
    {
        _name = inName;
        _weeklyCost = inWeeklyCost;
    }
    
    /**
     * 一覧表示用に toString() をオーバーライド
     * @return サプリメントの名前
     */
    @Override
    public String toString()
    {
        return getName();
    }
    
    /**
     * 名前フィールドの getter メソッド
     * @return 名前フィールド
     */
    public String getName()
    {
        return _name;
    }
    
    /**
     * 名前フィールドの setter メソッド
     * @param inName 名前フィールドに設定する名前
     */
    public void setName(String inName)
    {
        _name = inName;
    }
    
    /**
     * 週額料金フィールドの getter メソッド
     * @return 週額料金
     */
    public int getWeeklyCost()
    {
        return _weeklyCost;
    }
    
    /**
     * 週額料金フィールドの setter メソッド
     * @param inWeeklyCost 週額料金フィールドに設定する値
     */
    public void setWeeklyCost(int inWeeklyCost)
    {
        _weeklyCost = inWeeklyCost;
    }
}
