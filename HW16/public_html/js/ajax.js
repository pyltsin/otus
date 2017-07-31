function viewUsers(parse) {
    var root = document.getElementById("users");
    for (var i in parse) {
        var id = parse[i];
        var newLi = document.createElement('button');
        newLi.innerHTML = id;
        newLi.onclick = function () {
            return getUser(this);
        };
        newLi.addEventListener('onclick', function () {
            return getUser(this);
        });
        root.appendChild(newLi);
    }
}

function viewUser(parse) {
    var root = document.getElementById("user");
    root.innerHTML = "Name: " + parse.nameUser + "<br/>" + " Age: " + parse.ageUser + "<br/>" + "id: " + parse.id;
}

function getUser(node) {
    var id = 0 + node.innerHTML;
    console.log(id);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            viewUser(JSON.parse(this.responseText));
        } else {
            // alert("Server response: " + this.status);
        }
    };
    var data = 'iduser=' + id;

    xhttp.open("post", "/id?iduser=" + id, true);
    xhttp.send();
}


function getAllUser() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            viewUsers(JSON.parse(this.responseText));
        } else {
            // alert("Server response: " + this.status);
        }
    };

    xhttp.open("GET", "/user", true);
    xhttp.send();
}