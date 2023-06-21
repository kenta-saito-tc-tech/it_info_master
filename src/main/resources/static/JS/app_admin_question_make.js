const testDown= document.getElementById("testDown");
testDown.innerHTML = `
    <option class=testOption>（選択してください）</option>
    <option class=testOption value="1">データベース</option>
    <option class=testOption value="2">システム構成要素</option>
    <option class=testOption value="3">情報処理</option>
    <option class=testOption value="4">マネジメント</option>
    <option class=testOption value="5">ネットワーク</option>
    <option class=testOption value="6">セキュリティ</option>
`;
var selectedValue;

testDown.addEventListener("change", function() {
  selectedValue = testDown.value;
  console.log(selectedValue);
});


document.getElementById('add').addEventListener("click", () => {
    const questionName = document.getElementById('questionName').value;
    const questionText = document.getElementById('questionText').value;
    const answerText = document.getElementById('answerText').value;


    const data = {
        id: 1,
        questionName: questionName,
        questionText: questionText,
        answerText: answerText,
        categoryId: selectedValue,
    };

    const tail = [
                   { id: 1, choiceText: "Choice 1", answer: true, questionId: 6 },
                   { id: 2, choiceText: "Choice 2", answer: false, questionId: 6 },
                   { id: 3, choiceText: "Choice 3", answer: false, questionId: 6 },
                   { id: 4, choiceText: "Choice 4", answer: false, questionId: 6 }
                 ];

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
            window.alert(returnId);
            fetch('/api/choiceInsert', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(tail),
                        })
        })
      }
    })


})