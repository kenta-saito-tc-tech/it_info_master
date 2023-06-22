const testera= document.getElementById("testera");
testera.innerHTML = `
    <option class=testOption>（選択してください）</option>
    <option class=testOption value="1">2022</option>
    <option class=testOption value="2">2023</option>
    <option class=testOption value="3">2024</option>
    <option class=testOption value="4">2025</option>
    <option class=testOption value="5">2026</option>
    <option class=testOption value="6">2027</option>
`;

document.getElementById('buttonTest').addEventListener('click', () => {
  var testDisplay = document.getElementById("test1");
    if (testDisplay.style.display == "none") {
        testDisplay.style.display = "block";
    } else {
        testDisplay.style.display = "none";
    }

})

document.getElementById('buttonTest2').addEventListener('click', () => {
  var testDisplay2 = document.getElementById("test2");
    if (testDisplay2.style.display == "none") {
        testDisplay2.style.display = "block";
    } else {
        testDisplay2.style.display = "none";
    }

})

document.getElementById('buttonTest3').addEventListener('click', () => {
  var testDisplay3 = document.getElementById("test3");
    if (testDisplay3.style.display == "none") {
        testDisplay3.style.display = "block";
    } else {
        testDisplay3.style.display = "none";
    }

})

document.getElementById('buttonTest4').addEventListener('click', () => {
  var testDisplay4 = document.getElementById("test4");
    if (testDisplay4.style.display == "none") {
        testDisplay4.style.display = "block";
    } else {
        testDisplay4.style.display = "none";
    }

})

document.getElementById('buttonTest5').addEventListener('click', () => {
  var testDisplay5 = document.getElementById("test5");
    if (testDisplay5.style.display == "none") {
        testDisplay5.style.display = "block";
    } else {
        testDisplay5.style.display = "none";
    }

})