const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = process.env.PORT || process.env.DEFAULT_APP_PORT || 3000;

const server = http.createServer((req, res) => {
    // Enable CORS and iframe embedding
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

    if (req.method === 'OPTIONS') {
        res.writeHead(200);
        res.end();
        return;
    }

    if (req.url === '/api/health') {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ status: 'ok', time: new Date().toISOString() }));
        return;
    }

    // Serve public static files or index.html
    let filePath = path.join(__dirname, 'public', req.url === '/' ? 'index.html' : req.url);

    if (!fs.existsSync(filePath)) {
        filePath = path.join(__dirname, 'public', 'index.html');
    }

    const ext = path.extname(filePath);
    const contentTypes = {
        '.html': 'text/html; charset=utf-8',
        '.js': 'application/javascript; charset=utf-8',
        '.css': 'text/css; charset=utf-8',
        '.json': 'application/json; charset=utf-8',
        '.png': 'image/png',
        '.jpg': 'image/jpeg',
        '.svg': 'image/svg+xml'
    };

    const contentType = contentTypes[ext] || 'text/plain';

    fs.readFile(filePath, (err, content) => {
        if (err) {
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            res.end('Server Error: ' + err.message);
        } else {
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content);
        }
    });
});

server.listen(PORT, '0.0.0.0', () => {
    console.log(`[AX-01] Cyber Core Server active on http://0.0.0.0:${PORT}`);
});
