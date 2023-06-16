document.addEventListener("DOMContentLoaded", () => {
    fetch('/ages',{
    })
    .then(res=>{
        if(res.status===400){
            document.getElementById('error_massage').textContent='年代がありません';
        }else{
            res.json()
            .then(data=>{
                const selectElement = document.createElement('select');

                data.forEach((age) => {
                    const optionElement = document.createElement('option');
                    optionElement.value = age.id;
                    optionElement.textContent = age.age;
                    selectElement.appendChild(optionElement);
                });
                const selectAge = document.getElementById('js_select_age');
                selectAge.appendChild(selectElement);
            })
        }
    })
});