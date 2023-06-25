document.addEventListener("DOMContentLoaded", () => {
    const userId = document.getElementById('js-userId').value;
    const id = document.getElementById('js-questionId').value;
    const choiceId = document.getElementById('js-choiceId').value;
    const ageId = document.getElementById('js-ageId').value;
    console.log(ageId);

    const questionUserAgeId = [id, userId, ageId];
    const kanpekiCheck = document.getElementById('kanpeki_check');
    fetch(`/api/check_complete_check?questionUserAgeId=${questionUserAgeId}`)
      .then(res => {
        if(res.status === 400) {
          
        } else {
          res.json()
          .then(data => {
            if(data === 2){
                kanpekiCheck.checked = true;
            }
          })
        }
    });

    console.log('userid:',userId);
    console.log('questionid:', id);
    console.log('choiceId:', choiceId);

    
    const kanpekiBtn = document.getElementById('kanpekiBtn');
    kanpekiBtn.addEventListener('click', function () {   
        kanpekiCheck.checked = !kanpekiCheck.checked;
    });

    const finishBtn = document.getElementById('finishBtn');
    finishBtn.addEventListener('click', function () {
      
      if(kanpekiCheck.checked){
        
        fetch(`/api/user_check_complete`,{
          method: "PUT",
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(questionUserAgeId)
        })
        .then(res => {
          if(res.status === 400) {

          } else {
            res.json()
            .then(data => {
              
            })
          }
          location.href = '/question_select';
        });
      }else{

        fetch(`/api/user_check_not_complete`,{
          method: "PUT",
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(questionUserAgeId)
        })
        .then(res => {
          if(res.status === 400) {

          } else {
            res.json()
            .then(data => {
              
            })
          }
        });
      }
      location.href = '/question_select';
    });

    const choice1 = document.getElementById('choice1');
    const choice2 = document.getElementById('choice2');
    const choice3 = document.getElementById('choice3');
    const choice4 = document.getElementById('choice4');
    console.log('choice1:',choice1);

    if(choice1.value == choiceId){
      choice1.checked = true;
      choice1.disabled;

    }else if(choice2.value == choiceId){
      choice2.checked = true;
      choice2.disabled;

    }else if(choice3.value == choiceId){
      choice3.checked = true;
      choice3.disabled;

    }else if(choice4.value == choiceId){
      choice4.checked = true;
      choice4.disabled;

    }


});