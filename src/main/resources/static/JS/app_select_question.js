document.addEventListener("DOMContentLoaded", () => {
    var userId = document.getElementById('js-userId').value;
    console.log('userid:',userId)
    //年代メニューを表示
    fetch('/api/ages', {
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
                        let database_q = document.getElementById('database_q');
                        let system_arch_q = document.getElementById('system_arch_q');
                        let info_process_q = document.getElementById('info_process_q');
                        let network_q = document.getElementById('network_q');
                        let management_q = document.getElementById('management_q');
                        let security_q = document.getElementById('security_q');

                        selectElement.addEventListener('change', function () {
                            ageId = selectElement.value;
                            console.log('年代を確認する', ageId);
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                        });


                        let categoryId;
                        const database = document.getElementById('database');
                        database.addEventListener('click', () => {
                            database_q.textContent="";
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            console.log('カテゴリを押したときの年代',ageId);
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
                                                const databaseQ = document.getElementById('database_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    //問題1問の枠div
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_d');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    databaseQ.appendChild(span);
                                                    databaseQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();

                                                    databaseQ.appendChild(div);
                                                    div.appendChild(span);

                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);

                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答";
                                                                            divPerfectCheck.classList.add('unanswered');                                                                       
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                        })

                        const systemArch = document.getElementById('system_arch');
                        systemArch.addEventListener('click', () => {
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            categoryId = '2';
                            console.log('年代を確認',ageId);
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
                                                const systemArchQ = document.getElementById('system_arch_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_s');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    systemArchQ.appendChild(span);
                                                    systemArchQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();

                                                    systemArchQ.appendChild(div);
                                                    div.appendChild(span);

                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);

                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答"; 
                                                                            divPerfectCheck.classList.add('unanswered');                                                                       
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                            
                        })

                        const infoProcess = document.getElementById('info_process');
                        infoProcess.addEventListener('click', () => {
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            console.log('年代を確認する', ageId);
                            categoryId = '3';
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
                                                const infoProcessQ = document.getElementById('info_process_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_i');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    infoProcessQ.appendChild(span);
                                                    infoProcessQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();                     

                                                    infoProcessQ.appendChild(div);
                                                    div.appendChild(span);
                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);
                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答";
                                                                            divPerfectCheck.classList.add('unanswered');                                                                        
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                            
                        })

                        const network = document.getElementById('network');
                        network.addEventListener('click', () => {
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            categoryId = '4';
                            console.log('年代を確認する', ageId);
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
                                                const networkQ = document.getElementById('network_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_n');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    networkQ.appendChild(span);
                                                    networkQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();

                                                    networkQ.appendChild(div);
                                                    div.appendChild(span);
                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);
                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答";
                                                                            divPerfectCheck.classList.add('unanswered');                                                                        
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                        })

                        const management = document.getElementById('management');
                        management.addEventListener('click', () => {
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            categoryId = '5';
                            console.log('年代を確認する', ageId);
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
                                                const managementQ = document.getElementById('management_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_m');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    managementQ.appendChild(span);
                                                    managementQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();                     

                                                    managementQ.appendChild(div);
                                                    div.appendChild(span);
                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);
                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答";
                                                                            divPerfectCheck.classList.add('unanswered');                                                                        
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                        })

                        const security = document.getElementById('security');
                        security.addEventListener('click', () => {
                            database_q.textContent="";
                            system_arch_q.textContent="";
                            info_process_q.textContent="";
                            network_q.textContent="";
                            management_q.textContent="";
                            security_q.textContent="";
                            categoryId = '6';
                            console.log('年代を確認する', ageId);
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
                                                const securityQ = document.getElementById('security_q');
                                                let questionId;
                                                document.getElementById('error_massage').textContent = '';
                                                for (let i = 0; i < data.length; i++) {
                                                    let title = data[i].questionName;
                                                    console.log(title);
                                                    const div = document.createElement('div');
                                                    div.classList.add('question_se');
                                                    const span = document.createElement('span');                                                   
                                                    span.textContent = title;
                                                        
                                                    securityQ.appendChild(span);
                                                    securityQ.classList.add('question');

                                                    const perfectCheck = document.createElement('span');
                                                    const br = document.createElement('br');

                                                    questionId = data[i].id.toString();

                                                    securityQ.appendChild(div);
                                                    div.appendChild(span);
                                                    let divPerfectCheck = document.createElement('div');
                                                    div.appendChild(divPerfectCheck);
                                                    divPerfectCheck.appendChild(perfectCheck);
                                                    div.appendChild(br);

                                                    let userAgeQuestionId = [userId, ageId, questionId];
                                                    //選んだ年代、カテゴリに対して完璧チェックがあるかどうか
                                                    fetch('/api/user_check_perfect_check',{
                                                        method: 'POST',
                                                        headers: {
                                                            'Content-Type': 'application/json'
                                                        },
                                                        body: JSON.stringify(userAgeQuestionId)
                                                    })
                                                        .then(res => {         
                                                            if (res.status === 400) {
                                                                document.getElementById('error_massage').textContent = 'データを反映できませんでした';
                                                            } else {
                                                                res.json()
                                                                    .then(data => {
                                                                        document.getElementById('error_massage').textContent = '';
                                                                        console.log('perfect_check:',data);
                                                                        if(data === 2){
                                                                            perfectCheck.textContent = "完璧";
                                                                            divPerfectCheck.classList.add('perfect');
                                                                        }else if(data === 1){
                                                                            perfectCheck.textContent = "完璧チェック未完了";
                                                                            divPerfectCheck.classList.add('notPerfect');
                                                                        }else{
                                                                            perfectCheck.textContent = "未解答";
                                                                            divPerfectCheck.classList.add('unanswered'); 
                                                                                                                                                   
                                                                        }                                                                     
                                                                    });
                                                            }
                                                        });

                                                        span.addEventListener('click', (event) =>{
                                                            let title = event.target.textContent;
                                                            console.log('問題クリック',title);
                                                            console.log('問題クリック',ageId);
                                                            console.log('問題クリック',categoryId);

                                                            title = title.toString();
                                                            ageId = ageId;
                                                            categoryId = categoryId;
                                                            window.location.href = "/question_test?title=" + encodeURIComponent(title) + "&ageId=" + encodeURIComponent(ageId) + "&categoryId=" + encodeURIComponent(categoryId);
                                                        });                                                                                                   
                                                }
                                                

                                            });
                                    }                                
                                });
                        })

                    })
            }
        })
});
