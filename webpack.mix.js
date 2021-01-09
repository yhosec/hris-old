const mix = require('laravel-mix');

mix.js([
    'src/main/resources/assets/js/app.js',
    'src/main/resources/assets/js/off-canvas.js',
    'src/main/resources/assets/js/misc.js',
    'src/main/resources/assets/js/dashboard.js'
    ], 'src/main/resources/static/assets/js/app.js')
    .copy('node_modules/font-awesome/fonts/', 'src/main/resources/static/fonts/vendor/font-awesome')
    .copy('src/main/resources/assets/images', 'src/main/resources/static/images')
    .sass('src/main/resources/assets/sass/app.scss', 'src/main/resources/static/assets/css')
    .sass('src/main/resources/assets/sass/app-purple.scss', 'src/main/resources/static/assets/css');