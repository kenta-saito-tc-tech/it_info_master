window.onload = function() {
    displayAge();
    displayQuestion();
}

//全問題のリスト
var allList;
//作成ボタン表示、非表示（0：非表示、1：表示）
var select = 0;

const testera= document.getElementById("testera");
testera.innerHTML = `
    <option class=testOption value=0>（選択してください）</option>
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


//年代を全件取り出して一旦非表示する
function displayAge() {
    const displayList = document.getElementById('testera');
    fetch(`/api/adminAllAgeSelect`)
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

//問題を全件持ってきて、カテゴリ別に表示する
function displayQuestion() {
    fetch(`/api/adminAllQuestionSelect`)
    .then(res => {
        if(res.status === 400) {
            window.alert('問題を取得できませんでした。');
        } else {
            res.json()
            .then(data => {
                data.forEach((adminQuestion) => {
                    const pElement = document.createElement('p');
                    const inputElement = document.createElement('input');
                    pElement.style.display = 'none';
                    pElement.classList.add('questionName');
                    pElement.textContent = adminQuestion.questionName;

                    inputElement.style.display = 'none';
                    inputElement.classList.add('questions');
                    inputElement.type = 'checkbox'
                    inputElement.value = adminQuestion.id;

                    pElement.appendChild(inputElement);
                    if(adminQuestion.categoryId == 1) {
                        document.getElementById('test1').appendChild(pElement);
                    } else if(adminQuestion.categoryId == 2) {
                        document.getElementById('test2').appendChild(pElement);
                    } else if(adminQuestion.categoryId == 3) {
                        document.getElementById('test3').appendChild(pElement);
                    } else if(adminQuestion.categoryId == 4) {
                        document.getElementById('test4').appendChild(pElement);
                    } else {
                        document.getElementById('test5').appendChild(pElement);
                    }
                })
            })
        }
    })
}

//年代がチェックされたら...
var ageSelect = document.getElementById('testera');
ageSelect.addEventListener('change', () => {
    var selected = ageSelect.value;
    fetch(`/api/adminCheckAge`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({age: selected}),
    })
    .then(res => {
      if(res.status === 400) {
        window.alert('Hello error');
      } else {
        res.json()
        .then(data => {
            //表示されている問題のみを入れる変数リスト
            var visibleParagraphs = [];

            var classList = document.getElementsByClassName('questions');
            var questionNames = document.getElementsByClassName('questionName');
            //一度すべての問題を非表示にする
            for(var k = 0; k < classList.length; k++) {
                classList[k].checked = false;
                classList[k].style.display = 'none';
                questionNames[k].style.display = 'none';
            }
            //question_ageテーブルにある問題のみ表示する
            for(var i = 0; i < data.length; i++) {
                for(var j = 0; j < classList.length; j++) {
                    if(data[i].questionId == classList[j].value) {
                        console.log('成功');
                        questionNames[j].style.display = 'block';
                        classList[j].style.display = 'inline';
                        visibleParagraphs.push(classList[j]);
                        break;
                    }
                }
            }
            fetch(`/api/adminCheckGameAge`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({age: selected}),
            })
            .then(res => {
              if(res.status === 400) {
                window.alert('Hello error');
              } else {
                res.json()
                .then(data => {
                    select = 1;
                    for(var i = 0; i < data.length; i++) {
                        for(var j = 0; j < visibleParagraphs.length; j++) {
                            if(data[i].questionId == visibleParagraphs[j].value) {
                                select = 0;
                                console.log('成功');
                                visibleParagraphs[j].checked = true;
                                break;
                            }
                        }
                    }

                    //select = 0なら作成ボタン非表示、1なら表示
                    if(select == 0) {
                        document.getElementById('create').style.display = 'none';
                    } else {
                        document.getElementById('create').style.display = 'block';
                    }
                })
              }
            });
        })
      }
    });
})

//作成ボタンが押されたら...
var createButton = document.getElementById('create');
createButton.addEventListener('click', () => {
    //チェックされた問題を送る用の変数リスト
    var checkQuestionList = [];

    //チェックされた問題をcheckQuestionListに入れる
    var classList = document.getElementsByClassName('questions');
    var insertAge = document.getElementById('testera').value;
    for (var i = 0; i < classList.length; i++) {
        if (classList[i].checked) {
          selectedValue = classList[i].value;
          var item = { id: 0, ageId: insertAge, questionId: selectedValue, };
          checkQuestionList.push(item);
        }
    }

    //checkQuestionListをRestControllerに送る
    fetch(`/api/adminSetGameQuestion`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(checkQuestionList),
    })

    //成功したかどうかを判定
    .then((response) => {
        if (response.status === 200) {
            window.alert('ゲーム問題を振り分けました。');
        } else {
            window.alert('問題が発生しました。');
            return;
        }

        //ページを再読み込み
        location.reload();
    })
})
