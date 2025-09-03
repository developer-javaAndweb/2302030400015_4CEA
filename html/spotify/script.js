

console.log("Welcome to Spotify");

// initilizing the variable
let songIndex = 0;
let audioElement = new Audio('song/Naamumkin___Maalik___Rajkummar_Rao,_Manushi_Chhillar,Sachin–Jigar,_Shreya_Ghoshal,_Varun_J,Amitabh_B(256k).mp3');
let masterplay = document.getElementById('masterplay');
let myprogressbar = document.getElementById('myprogressbar');
let gif = document.getElementById('gif');
let mastersongname = document.getElementById('mastersongname');

songitems = Array.from(document.getElementsByClassName('songitem'));

let songs = [
    { songName: "Namumkin", filePath: 'song/Naamumkin___Maalik___Rajkummar_Rao,_Manushi_Chhillar,Sachin–Jigar,_Shreya_Ghoshal,_Varun_J,Amitabh_B(256k).mp3', coverpath: "download (1).jpg" },
    { songName: "Tu Hai Toh Mai Hu", filePath: 'song/Tu_Hain_Toh_Main_Hoon___Sky_Force___Akshay,_Sara,_Veer,_Tanishk_B,_Arijit_Singh,_Afsana_Khan,_Irshad(256k).mp3', coverpath: "download (2).jpg" },
    { songName: "Tu-Hi Hai Ashquii", filePath: 'song/Tu_Hi_Hai_Aashiqui_Lyrics_-_Arijit_Singh_,_Palak_Muchhal(256k).mp3', coverpath: "download (3).jpg" },
    { songName: "Ve_Kamelya", filePath: 'song/Ve_Kamleya_Mere_Nadan_Dil__Full_Song__Arijit_Singh___Shreya_Ghoshal_Alia___Pritam___Ranveer(256k).mp3', coverpath: "download(4).jpg" },
];


songitems.forEach((element, i) => {
    // console.log(element, i);
    element.getElementsByTagName("img")[0].src = songs[i].coverpath;
    element.getElementsByClassName("songname")[0].innerText = songs[i].songName;

});



// handle paly and pause
masterplay.addEventListener('click', () => {
    if (audioElement.paused || audioElement.currentTime <= 0) {
        audioElement.play();
        masterplay.classList.remove('fa-play-circle');
        masterplay.classList.add('fa-pause-circle');
        gif.style.opacity = 1;
    }
    else {
        audioElement.pause();
        masterplay.classList.remove('fa-circle-pause');
        masterplay.classList.add('fa-circle-play');
        gif.style.opacity = 0;


    }

});




// listen to events update progress
audioElement.addEventListener('timeupdate', () => {
    let progress = parseInt((audioElement.currentTime / audioElement.duration) * 100);
    myprogressbar.value = progress;
});




myprogressbar.addEventListener('change', () => {
    audioElement.currentTime = myprogressbar.value * audioElement.duration / 100;
});



const makeAllPlays = () => {

    Array.from(document.getElementsByClassName('songitemplay')).forEach((element) => {

        element.classList.remove('fa-circle-pause');
        element.classList.add('fa-circle-play');

    });
};


// individual song play

Array.from(document.getElementsByClassName("songitemplay")).forEach((element) => {
    element.addEventListener('click', (e) => {
        // console.log(e.target);
        makeAllPlays();
        songIndex = parseInt(e.target.id);
        e.target.classList.remove('fa-circle-play');
        e.target.classList.add('fa-circle-pause');
        audioElement.src = songs[songIndex].filePath;
        mastersongname.innerText = songs[songIndex].songName;

        audioElement.currentTime = 0;
        audioElement.play();
        masterplay.classList.remove('fa-circle-play');
        masterplay.classList.add('fa-circle-pause');
        gif.style.opacity = 1;
        // spotify/song/Tu_Hi_Hai_Aashiqui_Lyrics_-_Arijit_Singh_,_Palak_Muchhal(256k).mp3

    });
});



// next song

document.getElementById('next').addEventListener('click', () => {
    if (songIndex >= songs.lenght - 1) {
        songIndex = 0
    }
    else {
        songIndex++;
    }
    // audioElement.src = `song/${songIndex+1}Tu_Hi_Hai_Aashiqui_Lyrics_-_Arijit_Singh_,_Palak_Muchhal(256).mp3`;
    audioElement.src = songs[songIndex].filePath;
    mastersongname.innerText = songs[songIndex].songName;

    audioElement.currentTime = 0;
    audioElement.play();
    masterplay.classList.remove('fa-circle-play')
    masterplay.classList.add('fa-circle-pause');
});


// previous song
document.getElementById('previous').addEventListener('click', () => {
    if (songIndex <= 0) {
        songIndex = 0
    }
    else {
        songIndex--;
    }
    audioElement.src = songs[songIndex].filePath;
    // audioElement.src = song[index+1].Tu_Hi_Hai_Aashiqui_Lyrics_-_Arijit_Singh_,_Palak_Muchhal(256).mp3;
    mastersongname.innerText = songs[songIndex].songName;
    audioElement.currentTime = 0;
    audioElement.play();
    masterplay.classList.remove('fa-circle-play');
    masterplay.classList.add('fa-circle-pause');
});



// Volume Control
const volumeControl = document.getElementById('volumeControl');
const volumeIcon = document.getElementById('volumeIcon');

// Set initial volume (0.5 = 50%)
audioElement.volume = 0.5;

volumeControl.addEventListener('input', () => {
    audioElement.volume = volumeControl.value / 100;

    // Update icon based on volume level
    if (audioElement.volume === 0) {
        volumeIcon.className = "fa-solid fa-volume-xmark";
    } else if (audioElement.volume < 0.5) {
        volumeIcon.className = "fa-solid fa-volume-low";
    } else {
        volumeIcon.className = "fa-solid fa-volume-high";
    }
});


let isShuffle = false;
let isRepeat = false;

// Shuffle toggle
document.getElementById("shuffleBtn").addEventListener("click", () => {
    isShuffle = !isShuffle;
    document.getElementById("shuffleBtn").style.color = isShuffle ? "green" : "white";
});

// Repeat toggle
document.getElementById("repeatBtn").addEventListener("click", () => {
    isRepeat = !isRepeat;
    document.getElementById("repeatBtn").style.color = isRepeat ? "green" : "white";
});

// Auto-play next or repeat/shuffle logic
audioElement.addEventListener('ended', () => {
    if (isRepeat) {
        audioElement.currentTime = 0;
        audioElement.play();
    } else if (isShuffle) {
        songIndex = Math.floor(Math.random() * songs.length);
        audioElement.src = songs[songIndex].filePath;
        mastersongname.innerText = songs[songIndex].songName;
        audioElement.play();
        updateIcons();
    } else {
        document.getElementById("next").click();
    }
});

function updateIcons() {
    makeAllPlays();
    document.getElementById(songIndex).classList.remove('fa-circle-play');
    document.getElementById(songIndex).classList.add('fa-circle-pause');
    masterplay.classList.remove('fa-circle-play');
    masterplay.classList.add('fa-circle-pause');
    gif.style.opacity = 1;
}

const currentTimeEl = document.getElementById('current-time');
const durationEl = document.getElementById('total-duration');

// Update time display every second
audioElement.addEventListener('timeupdate', () => {
    let current = audioElement.currentTime;
    let duration = audioElement.duration;

    // Format time
    function formatTime(time) {
        let minutes = Math.floor(time / 60);
        let seconds = Math.floor(time % 60);
        return `${minutes}:${seconds < 10 ? '0' + seconds : seconds}`;
    }

    if (!isNaN(duration)) {
        currentTimeEl.innerText = formatTime(current);
        durationEl.innerText = formatTime(duration);
    }
});

