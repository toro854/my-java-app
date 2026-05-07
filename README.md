# フラッシュ暗算系のゲーム

## デプロイURL
https://my-java-app-q9xd.onrender.com

## 使用技術

 ■ バックエンド
- Java
- Spring Boot
- Spring MVC
- Spring Security

 ■ フロントエンド
- JavaScript
- HTML / CSS

 ■ データベース
- MySQL / PostgreSQL

 ■ インフラ・開発ツール
- Git / GitHub
- Render

 ## 使用技術
- ユーザー登録機能
- ログイン認証機能
- パスワードハッシュ化
- スコア保存機能
- ランキング機能
- DBによるデータ永続化

## ゲーム機能
### ■ モード
- 暗算モード（表示された数字の合計を回答）
- 記憶モード（表示された数字の順番を回答）

### ■ 難易度
- 複数レベル（表示速度・難易度が変化）

### ■ スコア機能
- チャレンジモードでスコア計測機能あり

- ## 📷 スクリーンショット

- ゲーム画面

<table>
  <tr>
    <td><img src="images/sukusyo001.png" width="300"></td>
    <td><img src="images/sukusyo002.png" width="300"></td>
  </tr>
  <tr>
    <td><img src="images/sukusyo003.png" width="300"></td>
    <td><img src="images/sukusyo004.png" width="300"></td>
  </tr>
</table>

## 工夫した点
- ゲームモードを分けて、異なる認知能力（計算力・記憶力）を鍛えられる設計にした
- レベル制を導入し、難易度調整を可能にした
- 一桁の数字しか出したくなかったので、左右反転や90度傾けて表示などして難易度アップするようにした
- 漢数字などを混ぜることによる難易度アップするようにした
- マウスのみやスマホでのタップ操作ができるように4択式にした
- フロントとロジックを分離して実装

## 今後の改善案
- 入力バリデーション強化やUI改善、ランキング更新処理の最適化など。


