const testDown= document.getElementById("testDown");
testDown.innerHTML = `
    <option class=testOption value="0">（選択してください）</option>
    <option class=testOption value="1">データベース</option>
    <option class=testOption value="2">システム構成要素</option>
    <option class=testOption value="3">情報処理</option>
    <option class=testOption value="4">マネジメント</option>
    <option class=testOption value="5">ネットワーク</option>
    <option class=testOption value="6">セキュリティ</option>
`;
var selectedValue = '0';

testDown.addEventListener("change", function() {
  selectedValue = testDown.value;
  console.log(selectedValue);
});


var testDownSpan = document.getElementById('testDownSpan');
var questionNameSpan = document.getElementById('questionNameSpan');
var questionTextSpan = document.getElementById('questionTextSpan');
var choiceTextSpan = document.getElementById('choiceTextSpan');
var answerTextSpan = document.getElementById('answerTextSpan');


document.getElementById('add').addEventListener("click", () => {
    const questionName = document.getElementById('questionName').value;
    const questionText = document.getElementById('questionText').value;
    const answerText = document.getElementById('answerText').value;
    var choiceTexts = document.getElementsByClassName('choiceTexts');
    var radios = document.getElementsByClassName('radio');
    var count;
    var tail = [];

    //カテゴリ名の判定
    if (selectedValue === '0') {
      testDownSpan.innerText = '入力してください';
      count = 1;
    } else {
      testDownSpan.innerText = '';
    }
    //問題タイトルの判定
    if (questionName === '') {
      questionNameSpan.innerText = '入力してください';
      count = 1;
    } else {
      questionNameSpan.innerText = '';
    }
    //問題文の判定
    if (questionText === '') {
      questionTextSpan.innerText = '入力してください';
      count = 1;
    } else {
      questionTextSpan.innerText = '';
    }
    //解説の判定
    if (answerText === '') {
      answerTextSpan.innerText = '入力してください';
      count = 1;
    } else {
      answerTextSpan.innerText = '';
    }
    //選択肢の判定
    if (choiceTexts[0].value === '' || choiceTexts[1].value === '' || choiceTexts[2].value === '' || choiceTexts[3].value === '') {
      choiceTextSpan.innerText = '入力してください';
      count = 1;
    } else {
      choiceTextSpan.innerText = '';
    }

    if (count === 1) {
      alert('いずれかの要素が空です。');
      return;
    }


    const data = {
        id: 1,
        questionName: questionName,
        questionText: questionText,
        answerText: answerText,
        categoryId: selectedValue,
    };


    // リクエストを送信
    fetch('/api/questionInsert', {
        method: 'POST', // リクエストメソッドを指定
        headers: {
            'Content-Type': 'application/json', // リクエストのヘッダーにJSON形式でデータを送信することを指定
        },
        body: JSON.stringify(data), // リクエストのボディにデータをJSON形式で変換して送信
    })
    .then((response) => {
      if (response.status === 400) {
        window.alert('問題が発生しました。');
      } else {
        response.json()
        .then(returnId => {
            for (var i = 0; i < choiceTexts.length; i++) {
              var choice = choiceTexts[i].value;
              var answer = radios[i].checked;
              var id = radios[i].value;

              var item = { id: id, choiceText: choice, answer: answer, questionId: returnId, };
              tail.push(item);
            }

            fetch('/api/choiceInsert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(tail),
            })
            .then((response) => {
                if (response.status === 200) {
                    window.alert('問題を追加しました。');
                } else {
                    window.alert('問題が発生しました。');
                }
            })
        })
      }
    })


})