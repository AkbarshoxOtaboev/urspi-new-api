# Frontend: HEMIS integratsiya API

Backend HEMIS ga **server orqali** ulanadi. Frontend **to‘g‘ridan-to‘g‘ri HEMIS** ga so‘rov yubormasligi kerak (CORS taqiqlangan). Faqat URSPI API chaqiriladi.

**Base URL:** `https://<api-host>`  
**Swagger:** `/swagger-ui.html` → taglar: `HEMIS Public Stats`, `HEMIS Config`, `HEMIS Integration`

---

## 1. Nima qayerda ishlatiladi

| Qism | Kim | Token | Endpoint |
|------|-----|-------|----------|
| Talabalar statistikasi (landing) | Public sayt | **Yo‘q** | `GET /api/hemis/public/stat-student` |
| HEMIS config (URL + token) | Admin panel | JWT + `HEMIS_CONFIG_*` | `/api/hemis-configs` |
| Ulanish testi / sync | Admin panel | JWT | `/api/hemis/connection-test`, `/api/hemis/sync/{resource}` |

**Muhim:** Public statistika ishlashi uchun admin avval aktiv HEMIS config yaratishi kerak (`baseUrl`, masalan `https://student.urspi.uz`).

---

## 2. Public: talabalar statistikasi (landing)

```http
GET /api/hemis/public/stat-student?l=uz-UZ
```

| Query | Qiymatlar | Izoh |
|-------|-----------|------|
| `l` | `uz-UZ`, `oz-UZ`, `ru-RU`, `en-US` | ixtiyoriy; default config `defaultLang` |

**Auth:** kerak emas.

**Javob o‘rami:**
```json
{
  "message": "HEMIS student statistics fetched",
  "data": {
    "success": true,
    "error": null,
    "code": 200,
    "data": {
      "education_type": {
        "Bakalavr": { "Erkak": 1298, "Ayol": 8214 },
        "Magistr": { "Erkak": 43, "Ayol": 310 },
        "Jami": { "Erkak": 1341, "Ayol": 8524 }
      },
      "age": { "...": "..." },
      "payment": { "...": "..." },
      "region": { "...": "..." },
      "citizenship": { "...": "..." },
      "accommodation": { "...": "..." },
      "education_form": { "...": "..." },
      "level": { "...": "..." }
    }
  }
}
```

### Frontend UI tavsiyasi

- `data.data.education_type.Jami` — umumiy Erkak/Ayol kartochkalari
- `education_type.Bakalavr` / `Magistr` — alohida bloklar
- `region`, `payment`, `citizenship` — jadval yoki chart (kalit = qator nomi, qiymat = object)
- Nested obyektlar dinamik: `Object.entries(...)` bilan render qiling
- Loading / error: `success === false` yoki HTTP 502 (HEMIS ishlamasa / config yo‘q)

---

## 3. Admin: HEMIS config

Permission: `HEMIS_CONFIG_VIEW|CREATE|EDIT|DELETE`

```http
POST   /api/hemis-configs
GET    /api/hemis-configs
GET    /api/hemis-configs/{id}
PUT    /api/hemis-configs/{id}
DELETE /api/hemis-configs/{id}
PUT    /api/hemis-configs/change/status/{id}
```

**Body (create/update):**
```json
{
  "name": "production",
  "baseUrl": "https://student.urspi.uz",
  "token": "auth_token_from_hemis_dashboard",
  "defaultLang": "uz-UZ",
  "active": true
}
```

- Bir vaqtda **bitta** `active: true`
- GET da token **mask**: `tokenMasked` (`****c123`), to‘liq token qaytmaydi
- Update da `token` bo‘sh qoldirilsa — eski token saqlanadi

---

## 4. Admin: connection-test va sync

```http
GET  /api/hemis/connection-test
POST /api/hemis/sync/DEPARTMENT
POST /api/hemis/sync/EMPLOYEE
```

- `connection-test` → `HEMIS_CONFIG_VIEW`
- `sync/*` → `HEMIS_CONFIG_EDIT`

Sync natijasi: `success`, `fetchedCount`, `upsertedCount`, `message`.  
Sync ma’lumotlari lokal cache jadvallarida (`hemis_departments`, `hemis_employees`) — landing uchun alohida public list hozircha yo‘q; asosan admin.

---

## 5. Cursor / AI uchun prompt (frontend)

Quyidagini frontend loyihasida Cursor Agent ga bering:

```text
URSPI backend orqali HEMIS statistikasini ulash. Frontend HEMIS ga to'g'ridan-to'g'ri so'rov yubormasin.

PUBLIC (landing, token kerak emas):
GET {API_BASE}/api/hemis/public/stat-student?l=uz-UZ
Javob: { message, data: { success, data: { education_type, age, payment, region, citizenship, accommodation, education_form, level } } }

education_type.Jami / Bakalavr / Magistr ichida Erkak, Ayol sonlari bor.
region, payment va boshqalar dinamik kalitli object — Object.entries bilan jadval/chart yasagin.

Til: l=uz-UZ|oz-UZ|ru-RU|en-US. UI tiliga mos qilib yubor.

ADMIN panel (agar kerak bo'lsa):
- JWT + HEMIS_CONFIG_* permissions
- CRUD /api/hemis-configs (baseUrl, token, active, defaultLang); GET da tokenMasked
- GET /api/hemis/connection-test
- POST /api/hemis/sync/DEPARTMENT|EMPLOYEE

Xato holatida (config yo'q / HEMIS down) loading + xabar ko'rsat.
Hujjat: docs/FRONTEND_HEMIS_API.md
Swagger: /swagger-ui.html → HEMIS Public Stats
```

---

## 6. Tezkor tekshiruv

```bash
# Public (config active bo'lishi kerak)
curl -s "https://<api-host>/api/hemis/public/stat-student?l=uz-UZ"

# Admin
curl -s -X POST "https://<api-host>/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"..."}'
# keyin Authorization: Bearer <accessToken>
```
