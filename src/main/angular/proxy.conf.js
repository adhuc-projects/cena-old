const PROXY_CONFIG = [
  {
      context: [
          "/authentication",
          "/api"
      ],
      target: "http://localhost:8080",
      secure: false,
      "changeOrigin": true
  }
]
module.exports = PROXY_CONFIG;
