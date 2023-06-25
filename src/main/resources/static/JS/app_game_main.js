window.onload = function() {
    displayAge();
}

function displayAge() {
    const displayList = document.getElementById('testera');
    fetch(`/api/gameAllAgeSelect`)
    .then(res => {
        if(res.status === 400) {
            window.alert('年代を取得できませんでした。');
        } else {
            res.json()
            .then(data => {
                data.forEach((age) => {
                    const optionElement = document.createElement('option');
                    optionElement.classList.add('questionList');
                    optionElement.value = age.id;
                    optionElement.textContent = age.age;

                    console.log(optionElement.value);
                    console.log(optionElement.innerText);

                    displayList.appendChild(optionElement);
                })
            })
        }
    })
}
