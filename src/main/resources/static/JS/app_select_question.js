document.addEventListener("DOMContentLoaded", () => {
    var userId = document.getElementById('js-userId').value;
    console.log('userid:',userId)

    fetch('/ages', {
    })
        .then(res => {
            if (res.status === 400) {
                document.getElementById('error_massage').textContent = '年代がありません';
            } else {
                res.json()
                    .then(data => {
                        const selectElement = document.createElement('select');
                        data.forEach((age) => {

                            const optionElement = document.createElement('option');
                            optionElement.value = age.id;
                            console.log(optionElement.value);                               //Idを取得できているかの確認

                            optionElement.textContent = age.age;
                            selectElement.appendChild(optionElement);
                        })

                        const selectAge = document.getElementById('js_select_age');
                        selectAge.appendChild(selectElement);


                        let ageId = '1';

                        selectElement.addEventListener('change', function () {
                            ageId = selectElement.value;
                            console.log('年代を確認する', ageId);
                        });


                        let categoryId;
                        const database = document.getElementById('database');
                        database.addEventListener('click', () => {
                            console.log(ageId);
                            categoryId = '1';
                            console.log('カテゴリを確認する', categoryId);
                            let ageCategory = [ageId, categoryId];


                            fetch('/api/selectQuestion',{
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(ageCategory)
                            })

                                .then(res => {

                                    if (res.status === 400) {
                                        document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                    } else {
                                        res.json()
                                            .then(data => {
                                                document.getElementById('error_massage').textContent = '';

                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const span = document.createElement('span');        // document.createElement('span');でHTMLのタグを作成している。
                                                    const br = document.createElement('br');
                                                    span.textContent = title;
                                                    const databaseQ = document.getElementById('database_q');
                                                    databaseQ.appendChild(span);
                                                    databaseQ.appendChild(br);
                                                }

                                            });
                                    }
                                });
                        })

                        const systemArch = document.getElementById('system_arch');
                        systemArch.addEventListener('click', () => {
                            categoryId = '2';
                            let ageCategory = [ageId, categoryId];
                        })

                        const infoProcess = document.getElementById('info_process');
                        infoProcess.addEventListener('click', () => {
                            categoryId = '3';
                            let ageCategory = [ageId, categoryId];
                        })

                        const network = document.getElementById('network');
                        network.addEventListener('click', () => {
                            categoryId = '4';
                            let ageCategory = [ageId, categoryId];
                        })

                        const management = document.getElementById('management');
                        management.addEventListener('click', () => {
                            categoryId = '5';
                            let ageCategory = [ageId, categoryId];
                        })

                        const security = document.getElementById('security');
                        security.addEventListener('click', () => {
                            categoryId = '6';
                        })

                    })
            }
        })
});
