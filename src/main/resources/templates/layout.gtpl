yieldUnescaped '<!DOCTYPE html>'
html(lang:'en') {
    head {
        meta(charset:'utf-8')
        title(title ?: 'Time and TimeZone Calculator')
        meta('http-equiv': "Content-Type", content:"text/html; charset=utf-8")
        meta(name: 'viewport', content: 'width=device-width, initial-scale=1.0')
        script(src: 'https://code.jquery.com/jquery-3.4.1.slim.min.js') {}
        script(src: 'https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js') {}
        script(src: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js') {}
        link(href: 'https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css', rel: 'stylesheet')
        link(href: '/css/bootstrap.min.css', rel: 'stylesheet')
        link(href: '/css/bootstrap-theme.min.css', rel: 'stylesheet')

    }
    body {
        div(class:'container') {
            bodyContents()
        }
    }
}
