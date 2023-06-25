document.addEventListener("DOMContentLoaded", () => { 
    console.log('app_random_answer.js'); 
    const yourAnswer = document.getElementById('js-yourAnswer');
    const judge = document.getElementById('judge');
    if(yourAnswer.value === 'true'){
        judge.textContent = '〇';
        judge.classList.add('correct');
    }else{
        judge.textContent = '×'
        judge.classList.add('incorrect');
    }

    const userId = document.getElementById('js-userId').value;
    const questionId = document.getElementById('js-questionId').value;
    const ageId = document.getElementById('js-ageId').value;

    console.log('random_answer/値確認');
    console.log('userId:',userId);
    console.log('questionId:',userId);
    console.log('ageId:',ageId);

    const questionUserAgeId = [questionId, userId, ageId];

    const perfectCheck = document.getElementById('perfect_check');
        fetch(`/api/check_complete_check?questionUserAgeId=${questionUserAgeId}`)
          .then(res => {
            if(res.status === 400) {

            } else {
              res.json()
              .then(data => {
                console.log('perfect_checkの値',data);
                if(data === 2){
                    perfectCheck.checked = true;
                }
              })
            }
        });
        const lookCheck = document.getElementById('look_check');
        fetch(`/api/check_look_check?questionUserAgeId=${questionUserAgeId}`)
          .then(res => {
            if(res.status === 400) {

            } else {
              res.json()
              .then(data => {
                console.log('look_checkの値',data);
                if(data === 2){
                    lookCheck.checked = true;
                }
              })
            }
        });
});