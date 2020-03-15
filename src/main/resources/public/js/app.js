document.getElementById("go").addEventListener("click", getTimeZone);

function getTimeZone() {

    var xhr = new XMLHttpRequest();
    var country = document.querySelector("#country").value;
    var city = document.querySelector("#city").value;
    xhr.open('GET', "/time?city=" + city + "&country=" + country, true);
    xhr.send();
    xhr.onreadystatechange = processRequest;

    function processRequest(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            var compiledTemplate = Handlebars.compile(document.querySelector("#results-template").innerHTML);
            if (response.errorMessage == null) {
                console.log(response)
                document.querySelector("#results-list-container").innerHTML = compiledTemplate(response);
                document.querySelector("#error-message").innerHTML = "";
            } else {
                document.querySelector("#results-list-container").innerHTML = "";
                document.querySelector("#error-message").innerHTML = "<span>" + response.errorMessage + "</span>";
            }
            document.querySelector("#results").style.cssText = 'padding: 1.5rem';
        }
    }

}