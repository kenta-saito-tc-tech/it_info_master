window.onload = function() {
    questionDisplay();
    selectDisplay();
    startStopwatch();
}

var count = 0;
var choiceCount = 0;
var questionList = [];
var choiceList = [];

document.getElementById('next').addEventListener('click', () => {
    getSelectedValue();
    count++;
    if(count == questionList.length) {
        document.getElementById("ok").style.display = "flex";
        document.getElementById('next').style.display = "none";
        stopStopwatch();
        return;
    } else {
        document.getElementById('ok').style.display = "none";
    }
    document.getElementById('i').innerText = count;

    //questionListの問題を入れてあげてる感じ
    document.getElementById('categoryName').textContent = questionList[count].categoryName;
    document.getElementById('questionName').textContent = questionList[count].questionName;
    document.getElementById('questionText').textContent = questionList[count].questionText;
    document.getElementById('questionId').textContent = questionList[count].questionId;

    //choiceListの選択肢を順番に入れてあげてる感じ
    var radioButtons = document.getElementsByName("select");
    var countI = 0;
    var memes = document.getElementsByClassName("meme");

    // すべてのラジオボタンのチェックを削除する
    for (var i = 0; i < radioButtons.length; i++) {
        radioButtons[i].checked = false;
    }


    while(true) {
          radioButtons[countI].value = choiceList[choiceCount].answer;
          memes[countI].textContent = choiceList[choiceCount].choiceText;
          countI++;
          choiceCount++;
          if(choiceCount % 4 == 0){
            break;
          }
    }
        // 一番上のラジオボタンにチェックを付ける
        radioButtons[0].checked = true;
})


function questionDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    fetch(`/question?userGameId=${id}&i=${i}`)
    .then(res => {
        if(res.status === 400) {
          window.alert('Hello JavaScript');
        } else {
          res.json()
          .then(data => {
            data.forEach((questions) => {
                questionList.push(questions);
            })
            document.getElementById('categoryName').textContent = data[0].categoryName;
            document.getElementById('questionName').textContent = data[0].questionName;
            document.getElementById('questionText').textContent = data[0].questionText;
            document.getElementById('questionId').textContent = data[0].questionId;
          })
        }
    });
}

function selectDisplay() {
    const id = document.getElementById('userGameId').innerText;
    const i = document.getElementById('i').innerText;
    fetch(`/game_start/select?userGameId=${id}&i=${i}`)
    .then(res => {
      if(res.status === 400) {
        window.alert('Hello error');
      } else {
        res.json()
        .then(data => {
        data.forEach((choices) => {
            choiceList.push(choices);
        })

        var radioButtons = document.getElementsByName("select");
        var countI = 0;
        var memes = document.getElementsByClassName("meme");

        // すべてのラジオボタンのチェックを削除する
        for (var i = 0; i < radioButtons.length; i++) {
            radioButtons[i].checked = false;
        }

        while(true) {
              radioButtons[countI].value = choiceList[choiceCount].answer;
              memes[countI].textContent = choiceList[choiceCount].choiceText;
              countI++;
              choiceCount++;
              if(choiceCount % 4 == 0){
                break;
              }
        }

            // 一番上のラジオボタンにチェックを付ける
            radioButtons[0].checked = true;
        })
      }
    });
}



function getSelectedValue() {
    // ラジオボタン要素の取得
    var radios = document.getElementsByName('select');
    var questionId = document.getElementById('questionId').innerText;
    var dateId = document.getElementById('userGameId').innerText;
    var selectedValue;
    var answerNum;

    // 選択されているラジオボタンのvalue値を取得
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
          selectedValue = radios[i].value;
          console.log(selectedValue); // 取得したvalue値をコンソールに表示
          break;
        }
    }

    if (selectedValue == 'true') {
        answerNum = 1;
        console.log(answerNum);
    } else {
        answerNum = 0;
        console.log(answerNum);
    }

    // 送信するデータを定義
    const data = {
        questionId: questionId,
        userAnswer: answerNum,
        dateId: dateId,
    };

    // リクエストを送信
    fetch('/game_start/answerInsert', {
        method: 'POST', // リクエストメソッドを指定
        headers: {
            'Content-Type': 'application/json', // リクエストのヘッダーにJSON形式でデータを送信することを指定
        },
        body: JSON.stringify(data), // リクエストのボディにデータをJSON形式で変換して送信
    })

}


// ストップウォッチの処理
var stopwatchInterval;
var stopwatchTime = 0;
var mm = document.getElementById("m");
var ss = document.getElementById("s");

function startStopwatch() {
  var stopwatchElement = document.getElementById("stopwatch");

  stopwatchInterval = setInterval(function() {
    stopwatchTime++;
    var minutes = Math.floor(stopwatchTime / 60);
    var seconds = stopwatchTime % 60;

    mm.innerText = minutes;
    ss.innerText = seconds;

    stopwatchElement.innerText = minutes + ":" + formatSeconds(seconds);
  }, 1000);
}

function stopStopwatch() {
  clearInterval(stopwatchInterval);
  var stopwatchElement = document.getElementById("stopwatch");
  const id = document.getElementById('userGameId').innerText;

  var m = mm.innerText;
  var s = ss.innerText;

  const data = {
          id: id,
          minutes: m,
          seconds: s,
      };

      // リクエストを送信
      var user = document.getElementById('userRole').innerText;

      if (user != 'admin') {
        fetch('/game_start/timeUpdate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // リクエストのヘッダーにJSON形式でデータを送信することを指定
            },
            body: JSON.stringify(data), // リクエストのボディにデータをJSON形式で変換して送信
        })
      }
}

// 秒を2桁表示にフォーマットする
function formatSeconds(seconds) {
  return seconds < 10 ? "0" + seconds : seconds;
}