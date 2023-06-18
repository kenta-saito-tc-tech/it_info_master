window.onload = function() {
    questionDisplay();
}

var count = 0;

document.getElementById('next').addEventListener('click', () => {
    count++;
    document.getElementById('i').innerText = count;
    questionDisplay();
//    const selected = //選択したvalue値が入る
//    fetch(`/game_start?select=${selected}`)
//      .then(res => {
//        if(res.status === 400) {
//          window.alert('Hello JavaScript');
//        } else {
//          res.json()
//          .then(data => {
//            document.getElementById('categoryName').textContent = data.category;
//            document.getElementById('questionName').textContent = data.question;
//            document.getElementById('questionText').textContent = data.questionText;
//            document.getElementById('select').textContent = data.select;
//          })
//        }
//    });
})

document.getElementById('back').addEventListener('click', () => {
    count--;
    document.getElementById('i').innerText = count;
    questionDisplay();
})

function questionDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    window.alert('test1');
    fetch(`/game_start?userGameId=${id}&i=${i}`)
    .then(res => {
        if(res.status === 400) {
          window.alert('Hello JavaScript');
        } else {
          res.json()
          .then(data => {
            document.getElementById('categoryName').textContent = data.categoryName;
            document.getElementById('questionName').textContent = data.questionName;
            document.getElementById('questionText').textContent = data.questionText;
            document.getElementById('questionId').textContent = data.questionId;

//            var radioButtons = document.getElementsByName("select");
//
//            // 既存のラジオボタンを新しい選択肢で更新する
//            radioButtons[0].textContent = data.choiceText;
//            radioButtons[1].textContent = data.choiceText2;
//            radioButtons[2].textContent = data.choiceText3;
//            radioButtons[3].textContent = data.choiceText4;
          })
        }
    });
    if(i == 4) {
        document.getElementById("ok").style.display = "flex";
    } else {
        document.getElementById('ok').style.display = "none";
    }
}

function selectDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    fetch(`/game_start_select?userGameId=${id}&i=${i}`)
    .then(res => {
      if(res.status === 400) {
        window.alert('Hello error');
      } else {
        res.json()
        .then(data => {

        var radioButtons = document.getElementsByName("select");

        // 既存のラジオボタンを新しい選択肢で更新する
        radioButtons[0].textContent = data.choiceText;
        radioButtons[1].textContent = data.choiceText2;
        radioButtons[2].textContent = data.choiceText3;
        radioButtons[3].textContent = data.choiceText4;
        })
      }
    });
}
