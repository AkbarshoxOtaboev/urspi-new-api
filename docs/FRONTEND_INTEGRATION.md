# Frontend + Backend birga ishlashi

Frontend manba: `frontend/` (https://github.com/Valijon0373/new.urspi.uz.git)  
Build natijasi Docker ichida `classpath:/static/` ga tushadi — API bilan **bir origin**.

## Docker (tavsiya / VDS)

```bash
cd /opt/urspi-new-api
git pull origin main
docker compose up -d --build api
```

Ochiladi:
- Sayt: `http://127.0.0.1:8080/`
- API: `http://127.0.0.1:8080/api/...`
- Swagger: `http://127.0.0.1:8080/swagger-ui.html`

Frontend `VITE_API_BASE_URL` bo‘sh → relative `/api` (proxy kerak emas).

## Lokal alohida ishlatish

**Terminal 1 — backend:**
```bash
# Windows
$env:SPRING_PROFILES_ACTIVE='dev'
.\mvnw.cmd -DskipTests spring-boot:run
```

**Terminal 2 — frontend (Vite):**
```bash
cd frontend
npm ci
npm run dev
```

Vite proxy: `/api` → `http://localhost:8080` (`frontend/vite.config.js`).

## Lokal jar ichida frontend bilan

```bash
cd frontend
npm ci
npm run build
# PowerShell
Remove-Item -Recurse -Force ..\src\main\resources\static -ErrorAction SilentlyContinue
Copy-Item -Recurse dist ..\src\main\resources\static
cd ..
.\mvnw.cmd -DskipTests spring-boot:run
```

`src/main/resources/static/` gitignore qilingan — faqat build/artefakt.
