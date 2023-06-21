document.addEventListener("DOMContentLoaded", () => {   
    function check(ageId, userId){
        // イベントリスナーを設定
    
            let count = document.category_form.category.length;
            console.log('ボックスの要素数',count);
          var flag = false; // 選択されているか否かを判定する変数
          let categories = [];
          let perfect = 0;
          for (var i = 0; i < document.category_form.category.length; i++) {
     
            // i番目のチェックボックスがチェックされているかを判定
              if (document.category_form.category[i].checked) {
                flag = true;
                categories.push(document.category_form.category[i].value);                    
              }             
          }      
          if(ageId === 0){
            flag = true;
          }
          // 何も選択されていない場合の処理   
          if (!flag) {
            alert("年代/項目が選択されていません。");
          }
          else if(flag){

            const category1 = document.getElementById('category1');
            const category2 = document.getElementById('category2');
            const category3 = document.getElementById('category3');
            const category4 = document.getElementById('category4');
            const category5 = document.getElementById('category5');
            const category6 = document.getElementById('category6');

            //チェック内容保存用
            let categories1 = [];

            for(let i = 0; i < document.category_form.category.length; i++){
                if(document.category_form.category[i].checked){
                    categories1.push(1);
                }else{
                    categories1.push(0);
                }
            }
            console.log('チェック状況',categories1);

            const userCategory = {
                categorySelect : categories1,
                ageId : ageId,
                userId : userId
            }
            //チェック状況をアップデート
            fetch(`/api/category_select_update`,{
                method: "PUT",
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify(userCategory)
              })
              .then(res => {
                if(res.status === 400) {
      
                } else {
                  res.json()
                  .then(data => {
                    
                  })
                }
                
              });
          
          const perfectCheck = document.getElementById('perfect_check');
          if(perfectCheck.checked){
            perfect = 1;
            //選択したカテゴリ
            console.log('選択したカテゴリ:',categories);
            console.log('選択した年代ID:',ageId);
            console.log('完璧チェック:',perfect);
            console.log('userId:',userId);
            const ageCategories = {
                ageId : ageId,
                categories : categories,
                perfect : perfect,
                userId : userId
            }


            fetch('/api/selectRandom',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ageCategories)
            })
                .then(res => {
                    if (res.status === 400) {
                        document.getElementById('error_massage').textContent = '問題情報がありません';
                    } else {
                        res.json()
                            .then(data => {
                                document.getElementById('error_message').textContent = '';
                                window.location.href = "/random_main?param=" + encodeURIComponent(JSON.stringify(data));
                               
                                // fetch(`/random_main?questionRecords=${data}`)
                                //     body: JSON.stringify(data)
                                //   .then(response => {
                                //     // レスポンスのデータを処理する処理を記述する
                                //   })
                                //   .catch(error => {
                                //     // エラーハンドリングを行う
                                //   })
                            });
                    }
                });
          }else{
            //選択したカテゴリ
            console.log('選択したカテゴリ:',categories);
            console.log('選択した年代ID:',ageId);
            console.log('完璧チェック:',perfect);
            console.log('userId:',userId);
            const ageCategories = {
                ageId : ageId,
                categories : categories,
                perfect : perfect,
                userId : userId
            }


            fetch('/api/selectRandom',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ageCategories)
            })
                .then(res => {
                    if (res.status === 400) {
                        document.getElementById('error_massage').textContent = '問題情報がありません';
                    } else {
                        res.json()
                            .then(data => {                      
                                document.getElementById('error_message').textContent = '';
                                window.location.href = "/random_main?param=" + encodeURIComponent(JSON.stringify(data));
                        });
                            

                    }
                });
          }
    
    }
}


    const category1 = document.getElementById('category1');
    const category2 = document.getElementById('category2');
    const category3 = document.getElementById('category3');
    const category4 = document.getElementById('category4');
    const category5 = document.getElementById('category5');
    const category6 = document.getElementById('category6');
    const perfect_check = document.getElementById('perfect_check');

    const userId = document.getElementById('js-userId').value;
    console.log('userId:',userId);

    fetch('/api/ages', {
    })
        .then(res => {
            if (res.status === 400) {
                document.getElementById('error_massage').textContent = '年代がありません';
            } else {
                res.json()
                .then(data => {
                    const selectElement = document.createElement('select');
                    selectElement.id = ''
                    const optionElementDefault = document.createElement('option');
                    optionElementDefault.value = 0;
                    optionElementDefault.textContent = '年代を選択してください'
                    selectElement.appendChild(optionElementDefault);
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
                        
                        //年代ID
                        ageId = selectElement.value;

                        console.log('年代を確認する', ageId);

                        const ageUserId = [ageId,userId];
                        console.log(ageUserId);

                        fetch(`/api/findRandSelect?ageUserId=${ageUserId}`)
                        
                        .then(res => {
                            category1.checked = false;
                            category2.checked = false;
                            category3.checked = false;
                            category4.checked = false;                        
                            category5.checked = false;
                            category6.checked = false;
                            perfect_check.checked = false;

                            if(res.status === 400) {
                            document.getElementById('error_message').textContent = '情報がありませんでした';
                            } else {
                                document.getElementById('error_message').textContent = '';
                            res.json()
                            .then(data => {
                                console.log('年代→カテゴリ',data);
                                console.log('カテゴリ1',category1.value);
                                console.log('カテゴリ2',category2.value);
                                console.log('カテゴリ3',category3.value);
                                console.log('カテゴリ4',category4.value);
                                console.log('カテゴリ5',category5.value);
                                console.log('カテゴリ6',category6.value);
                                const categories = [category1, category2, category3, category4, category5, category6];
                                // for (let i = 0; i < data.length; i++) {
                                //     let checkCategory = data[i].id;
                                //     console.log('チェックするcotegrory_id',checkCategory);
                                    //カテゴリの数だけ回す
                                    for (const c of categories) {
                                        c.checked = data.some(e => e.id == c.value)  
                                    }
                                                                
                            })
                            
                            }
                        });
                        
                        

                    });  
                    
                    
                    
                    const randomStartBtn = document.getElementById("random_start_btn");
                    // イベントリスナーを設定
                    randomStartBtn.addEventListener("click", function () {   
                        console.log('年代ID',ageId);
                        check(ageId,userId);  
                       
                    })                     
                })
            }
        })


    




});