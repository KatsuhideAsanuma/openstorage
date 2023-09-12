

# **API 設計文書**

## **APIリスト**

###### **1. 認証と認可**

- **ログイン**:
  - **成功時のHTTPステータス**: `200 OK`
  - **失敗時のHTTPステータス**: `401 Unauthorized`
  - **エンドポイント**: `/auth/login/async`
  - **メソッド**: `POST`
  - **リクエスト**:
```
{
  "type": "object",
  "properties": {
    "username": {"type": "string"},
    "password": {"type": "string"}
  },
  "required": ["username", "password"]
}
```
  - **レスポンス**:
```
{
  "type": "object",
  "properties": {
    "token": { "type": "string" }
  },
  "required": ["token"]
}
```
- **ログアウト**:
  - **エンドポイント**: `/auth/logout/async`
  - **メソッド**: `POST`### **2. 会員登録**

- **エンドポイント**: `/users/register/async`
- **メソッド**: `POST`
- **リクエスト**: {
  "type": "object",
  "properties": {
    "username": {"type": "string"},
    "password": {"type": "string"},
    "email": {"type": "string", "format": "email"},
    "user_type": {"type": "string"}
  },
  "required": ["username", "password", "email", "user_type"]
}

- **レスポンス**: {
  "type": "object",
  "properties": {
    "user_id": {"type": "integer"},
    "message": {"type": "string"}
  },
  "required": ["user_id", "message"]
}


- **エラーハンドリング**:
  - 既に存在するメールアドレス: `{ 'error': 'Email already registered.' }`
  - 不正なユーザータイプ: `{ 'error': 'Invalid user type.' }`

### **3. ユーザー情報のCRUD**

- **取得**: 
  - **エンドポイント**: `/users/{user_id}/async`
  - **メソッド**: `GET`
  
- **更新**:
  - **エンドポイント**: `/users/{user_id}/async`
  - **メソッド**: `PUT`
  - **リクエスト**: { `username`, `email`, ... }
  
- **削除**:
  - **エンドポイント**: `/users/{user_id}/async`
  - **メソッド**: `DELETE`

- **エラーハンドリング**:
  - 存在しないユーザーID: `{ 'error': 'User not found.' }`

- **json**:
{
  "type": "object",
  "properties": {
    "username": { "type": "string" },
    "email": { "type": "string", "format": "email" }
    // ... other fields as required
  },
  "required": ["username", "email"]
}


### **4. メディアのCRUD**

#### **画像**

- **作成**:
  - **エンドポイント**: `/api/v1/images/async`
  - **メソッド**: `POST`
  
- **読み取り**:
  - **エンドポイント**: `/api/v1/images/async/:id`
  - **メソッド**: `GET`

- **更新**:
  - **エンドポイント**: `/api/v1/images/async/:id`
  - **メソッド**: `PUT`

- **削除**:
  - **エンドポイント**: `/api/v1/images/async/:id`
  - **メソッド**: `DELETE`

#### **テキスト**

- **作成**:
  - **エンドポイント**: `/api/v1/texts/async`
  - **メソッド**: `POST`
  
- **読み取り**:
  - **エンドポイント**: `/api/v1/texts/async/:id`
  - **メソッド**: `GET`

- **更新**:
  - **エンドポイント**: `/api/v1/texts/async/:id`
  - **メソッド**: `PUT`

- **削除**:
  - **エンドポイント**: `/api/v1/texts/async/:id`
  - **メソッド**: `DELETE`

#### **映像**

- **作成**:
  - **エンドポイント**: `/api/v1/videos/async`
  - **メソッド**: `POST`
  
- **読み取り**:
  - **エンドポイント**: `/api/v1/videos/async/:id`
  - **メソッド**: `GET`

- **更新**:
  - **エンドポイント**: `/api/v1/videos/async/:id`
  - **メソッド**: `PUT`

- **削除**:
  - **エンドポイント**: `/api/v1/videos/async/:id`
  - **メソッド**: `DELETE`

#### **音楽**

- **作成**:
  - **エンドポイント**: `/api/v1/music/async`
  - **メソッド**: `POST`
  
- **読み取り**:
  - **エンドポイント**: `/api/v1/music/async/:id`
  - **メソッド**: `GET`

- **更新**:
  - **エンドポイント**: `/api/v1/music/async/:id`
  - **メソッド**: `PUT`

- **削除**:
  - **エンドポイント**: `/api/v1/music/async/:id`
  - **メソッド**: `DELETE`

- **POSTリクエスト**:
{
  "type": "object",
  "properties": {
    "{type}_data": { "type": "string", "format": "base64" },
    "metadata": { "type": "object" }
    // ... other filtering criteria as required
  },
  "required": ["{type}_data"]
}

-**GETリクエスト**:
{
  "type": "object",
  "properties": {
    "created_after": { "type": "string", "format": "date-time" },
    "created_before": { "type": "string", "format": "date-time" },
    "contains": { "type": "string" },
    "length_greater_than": { "type": "integer" },
    "duration_greater_than": { "type": "integer" },
    "genre": { "type": "string" },
    "resolution": { "type": "string" }
    // ... other filtering criteria as required
  }
}

- **GETレスポンス**:
{
  "type": "object",
  "properties": {
    "id": { "type": "integer" },
    "title": { "type": "string" },
    "description": { "type": "string" },
    "url": { "type": "string" },
    "created_at": { "type": "string", "format": "date-time" }
  },
  "required": ["id", "title", "description", "url", "created_at"]
}



### **4. APIキーの管理**

- **発行**: 
  - **エンドポイント**: `/api-key/generate/async`
  - **メソッド**: `POST`
  - **レスポンス**: {
  "type": "object",
  "properties": {
    "api_key": { "type": "string" }
  },
  "required": ["api_key"]
}

  
- **無効化**:
  - **エンドポイント**: `/api-key/invalidate/async`
  - **メソッド**: `DELETE`
  - **リクエスト**: {
  "type": "object",
  "properties": {
    "api_key": { "type": "string" }
  },
  "required": ["api_key"]
}


### **5. フィルタリング**

- **基本クエリ設計**:
  - キーワードによる検索
  
- **拡張**:
  - 深い階層のデータに対してもクエリ可能
  - 使用技術：GraphQL

### **6. ストリーミング**

- **音楽ストリーミング**:
  - **エンドポイント**: `/streaming-ws/music/{id}`
  - **メソッド**: `GET`
  
- **ビデオストリーミング**:
  - **エンドポイント**: `/streaming-ws/video/{id}`
  - **メソッド**: `GET`

- **レスポンス**：
{
  "type": "object",
  "properties": {
    "stream_url": { "type": "string", "format": "uri" }
  },
  "required": ["stream_url"]
}


### **7. タグとメタデータの管理**

- **登録**:
  - **エンドポイント**: `/metadata/async`
  - **メソッド**: `POST`
  - **リクエスト**:{
  "type": "object",
  "properties": {
    "tag": { "type": "string" },
    "metadata_value": { "type": "string" }
  },
  "required": ["tag", "metadata_value"]
}


- **取得**:
  - **エンドポイント**: `/metadata/async/{tag}`
  - **メソッド**: `GET`
  -**レスポンス**:
  {
  "type": "object",
  "properties": {
    "tag": { "type": "string" },
    "metadata_value": { "type": "string" }
  },
  "required": ["tag", "metadata_value"]
}


- **更新**:
  - **エンドポイント**: `/metadata/async/{tag}`
  - **メソッド**: `PUT`
  - **リクエスト**:{
  "type": "object",
  "properties": {
    "tag": { "type": "string" },
    "metadata_value": { "type": "string" }
  },
  "required": ["tag", "metadata_value"]
}
- **削除**:
  - **エンドポイント**: `/metadata/async/{tag}`
  - **メソッド**: `DELETE`

### **8. リクエスト・レスポンスのフォーマット**

- **推奨**: JSON

### **9. ページネーション、ソート、フィルタリング**

- クエリパラメーターによる指定

### 非同期処理

- /async の付されたエンドポイントはAJAX技術を利用して処理
- -wsの付されたエンドポイントはwebsocket技術を利用して処理
