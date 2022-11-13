function myFunction() {
    let x = document.getElementById("myDIV");
    let buttonText = document.getElementById("buttonText");
    if (x.style.display === "block") {
        x.style.display = "none";
        buttonText.innerText="Show solution";
    } else {
        x.style.display = "block";
        buttonText.innerText="Hide solution"
    }
}