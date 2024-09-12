// document.addEventListener('DOMContentLoaded', function() {
window.onload = function () {
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfTokenValue = document.querySelector('meta[name="_csrf"]').content;

    let isLoggedIn = false; // 로그인 상태를 저장할 변수
    let originalBgImageInfo = null; // 원래의 배경 이미지 정보를 저장할 변수

    let tempBgImageUuid = null;
    let tempBgImageName = null;
    let tempBgImageUrl = null;

    // Fetch User Setting
    fetch('/api/settings/get')
        .then(response => response.json())
        .then(data => {

            isLoggedIn = data.userId !== 'anonymousUser'; // 로그인 상태 저장

            document.getElementById('duration').value = data.duration;
            document.getElementById('backgroundColor').value = data.bgColor;
            document.getElementById('shadowColor').value = data.shadowColor;
            document.getElementById('textColor').value = data.txtColor;
            //document.getElementById('backgroundImage').value = data.bgImgName;

            // 로드된 설정을 적용
            if (isLoggedIn && data.bgImgUrl) {
                originalBgImageInfo = {
                    uuid: data.bgImgUuid,
                    name: data.bgImgName,
                    url: data.bgImgUrl
                };

                const imageUrl = `/uploads/${data.bgImgUrl}`;
                document.body.style.backgroundImage = `url(${imageUrl})`;
                document.body.style.backgroundSize = 'cover';
                document.body.style.backgroundPosition = 'center';

                // UI 업데이트
                document.getElementById('fileName').textContent = data.bgImgName;
                document.getElementById('fileInfo').style.display = 'block';
                document.getElementById('backgroundImage').style.display = 'none';
            }
            document.documentElement.style.setProperty('--color-primary', data.bgColor);
            document.documentElement.style.setProperty('--color-shadow', data.shadowColor);
            document.documentElement.style.setProperty('--color-timer-font', data.txtColor);

            currentDuration = data.duration;
            remainingTime = currentDuration * 60;
            updateDisplay();
        })
        .catch((error) => {
            console.error('Error:', error);
        });

    // Setting 기능 구현
    // variables
    const minutesDisplay = document.getElementById('minutes');
    const secondsDisplay = document.getElementById('seconds');
    const durationSelect = document.getElementById('duration');
    const applyDurationButton = document.getElementById('applyDuration');

    // change background color
    document.getElementById('backgroundColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-primary', this.value);
    });

    // change shadow color
    document.getElementById('shadowColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-shadow', this.value);
    });

    // change font color
    document.getElementById('textColor').addEventListener('input', function() {
        document.documentElement.style.setProperty('--color-timer-font', this.value);
    });

    // change background Image
    document.getElementById('backgroundImage').addEventListener('change', function(e) {
       const file = e.target.files[0];
       if(file) {
           const formData = new FormData();
           formData.append('file', file);

           fetch('/api/settings/uploadBackgroundImage', {
               method: 'POST',
               body: formData,
               headers: {
                   [csrfHeaderName]: csrfTokenValue,
               },
           })
               .then(response => response.json())
               .then(data => {
                   const imageUrl = `/uploads/${data.uploadUrl}`;
                   document.body.style.backgroundImage = `url(${imageUrl})`;
                   document.body.style.backgroundSize = 'cover';
                   document.body.style.backgroundPosition = 'center';
                   tempBgImageUuid = data.uuid;
                   tempBgImageName = data.name;
                   tempBgImageUrl = data.uploadUrl;

                   // UI 업데이트
                   document.getElementById('fileName').textContent = tempBgImageName;
                   document.getElementById('fileInfo').style.display = 'block';
                   document.getElementById('backgroundImage').style.display = 'none';
               })
               .catch(error => console.error('Error:', error));
       }
    });

    function deleteImageFile(bgImgUrl) {
        return fetch('/api/settings/deleteBackgroundImage', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            body: JSON.stringify({ "bgImgUrl" : bgImgUrl }),
            credentials: "same-origin",
            keepalive: true
        })
        .then(response => response.text())
        .then(data => data === 'success')
        .catch(error => {
            console.error('Error:', error);
            return false;
        });
    }

    // delete background Image file
    document.getElementById('removeFile').addEventListener('click', function (e) {
        e.preventDefault();

        // 배경 이미지 제거
        document.body.style.backgroundImage = '';
        document.body.style.backgroundColor = document.body.style.getPropertyValue('--color-primary');

        // 이미지 파일 삭제
        if(tempBgImageUrl) {
            deleteImageFile(tempBgImageUrl)
                .then(success => {
                    if (success) {
                        console.log('Temporary background image deleted successfully.');
                    } else {
                        console.error('Failed to delete temporary background image.');
                    }
                });
        }

        // 임시 변수 초기화
        tempBgImageUuid = null;
        tempBgImageName = null;
        tempBgImageUrl = null;

        // UI 초기화
        document.getElementById('backgroundImage').value = '';
        document.getElementById('backgroundImage').style.display = 'block';
        document.getElementById('fileName').textContent = '';
        document.getElementById('fileInfo').style.display = 'none';

    });

    // Save User Setting
    document.getElementById('saveSettings').addEventListener('click', function() {
        if(!isLoggedIn) {
            alert('To save your settings, please log in or create an account.');
            return
        }

        const settings = {
            duration: parseInt(document.getElementById('duration').value),
            txtColor: document.getElementById('textColor').value,
            shadowColor: document.getElementById('shadowColor').value,
            bgColor: document.getElementById('backgroundColor').value,
            bgImgUuid: tempBgImageUuid || (originalBgImageInfo ? originalBgImageInfo.uuid : null),
            bgImgName: tempBgImageName || (originalBgImageInfo ? originalBgImageInfo.name : null),
            bgImgUrl: tempBgImageUrl || (originalBgImageInfo ? originalBgImageInfo.url : null)
        };

        fetch('/api/settings/save', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            body: JSON.stringify(settings),
            credentials: "same-origin",
        })
            .then(response => response.text())
            .then(data => {
                if(data === 'success') {
                    alert('Your Setting has been successfully saved.');

                    originalBgImageInfo = {
                        uuid: settings.bgImgUuid,
                        name: settings.bgImgName,
                        url: settings.bgImgUrl
                    };

                    tempBgImageUuid = null;
                    tempBgImageName = null;
                    tempBgImageUrl = null;
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    });


    // 로그아웃 csrf 토큰 전송
    $("#logoutButton").on("click", function (e) {
        e.preventDefault();
        $("#logoutForm").submit();
    });


    // 타이머 기능 구현
    // variables
    const startButton = document.getElementById('startButton');
    const stopButton = document.getElementById('stopButton');
    const resetButton = document.getElementById('resetButton');

    let timer;
    let isTimerRunning = false; // 타이머가 동작 중인지 체크
    let currentDuration;
    let originalSessionDuration;
    let remainingTime;
    let currentTimerSessionId = document.getElementById('currentTimerSessionId').value;

    function updateDisplay() {
        const minutes = Math.floor(remainingTime / 60);
        const seconds = remainingTime % 60;
        minutesDisplay.textContent = String(minutes).padStart(2, '0');
        secondsDisplay.textContent = String(seconds).padStart(2, '0');
    }

    function startTimer() {
        if (isLoggedIn && !currentTimerSessionId) {
            createNewTimerSession(); // Create a new session if no current session exists
            originalSessionDuration = currentDuration;
        }

        if(!isTimerRunning) {
            isTimerRunning = true;
            timer = setInterval(function() {
                if (remainingTime > 0) {
                    remainingTime--;
                    updateDisplay();
                } else {
                    stopTimer(); // Automatically stop when time runs out
                }
            }, 1000);
        }

    }

    function stopTimer() {
        if(isTimerRunning) {
            clearInterval(timer);
            isTimerRunning = false;
            if (currentTimerSessionId) {
                const usedTime = Math.max(0, originalSessionDuration * 60 - remainingTime);
                updateTimerSession(usedTime);
            }
        }
    }

    function resetTimer() {
        if(isTimerRunning) {
            stopTimer();
        }
        remainingTime = currentDuration * 60;
        originalSessionDuration = null;
        updateDisplay();
        currentTimerSessionId = null; // Reset current session ID
        document.getElementById('currentTimerSessionId').value = '';
    }

    function createNewTimerSession() {
        fetch('/api/statistics/createTimerSession', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            credentials: "same-origin",
        })
            .then(response => response.text())
            .then(data => {
                currentTimerSessionId = data;
                console.log("created Timer Session Id : ", currentTimerSessionId);
                document.getElementById('currentTimerSessionId').value = currentTimerSessionId;
            })
            .catch(error => console.error('Error creating session:', error));
    }

    function updateTimerSession(usedTime) {
        if (!currentTimerSessionId) return;

        fetch('/api/statistics/updateTimerSession', {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeaderName]: csrfTokenValue,
            },
            body: JSON.stringify({ sessionId: currentTimerSessionId, usedTime: Math.max(0, usedTime) }),
            credentials: "same-origin",
            keepalive: true
        })
            .then(response => response.text())
            .then(data => console.log('Session updated:', data))
            .catch(error => console.error('Error updating session:', error));
    }

    startButton.addEventListener('click', function() {
        startTimer();
        startButton.style.display = 'none';
        stopButton.style.display = 'inline-block';

    });
    stopButton.addEventListener('click', function() {
        stopTimer();
        startButton.style.display = 'inline-block';
        stopButton.style.display = 'none';
    });
    resetButton.addEventListener('click', function() {
        resetTimer();
        startButton.style.display = 'inline-block';
        stopButton.style.display = 'none';
    });
    // change duration
    applyDurationButton.addEventListener('click', function () {
        currentDuration = parseInt(durationSelect.value);
        resetTimer();
    });

    // Handle page unload event
    window.addEventListener('beforeunload', function(e) {
        if (currentTimerSessionId && isTimerRunning) {
            e.preventDefault();
            updateTimerSession(originalSessionDuration * 60 - remainingTime); // Update session before leaving the page
        }
        if (tempBgImageUrl) {
            e.preventDefault();
            deleteImageFile(tempBgImageUrl)
                .then(success => {
                    if (success) {
                        // 임시 변수 초기화
                        tempBgImageUuid = null;
                        tempBgImageName = null;
                        tempBgImageUrl = null;
                    } else {
                        console.error('Failed to delete temporary background image.');
                    }
                });
        }
    });

}
// });
