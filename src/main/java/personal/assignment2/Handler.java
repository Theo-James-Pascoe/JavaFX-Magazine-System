/**
 * 作成者: パスコー・セオ・ジェームズ
 * 目的: ハンドラー用の抽象基底クラス（Handler）
 */

package personal.assignment2;

import javafx.scene.control.Alert;

/**
 * ハンドラーが継承する抽象基底クラス。
 * 提供するメソッドは 1 つだけだが、課題 1 のフィードバックを踏まえ、
 * 可能な限りクラスとして責務を分離する設計とした。
 * @author パスコー・セオ・ジェームズ
 */
public abstract class Handler 
{
    /**
     * 画面上に警告を表示するメソッド
     * @param title 警告のタイトル
     * @param message 警告の詳細／メッセージ
     */
    protected void showWarning(String title, String message) 
    {
        Alert alertToShow = new Alert(Alert.AlertType.WARNING);
        
        alertToShow.setTitle(title);
        alertToShow.setHeaderText(null);
        alertToShow.setContentText(message);
        alertToShow.showAndWait();
    }
}
