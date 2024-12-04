var postits = document.getElementsByClassName("postit");

function init(){	
	for (let i of postits) {
		var randomRotation = (Math.random() * 6) - 3;
		i.style.transform = 'rotate(' + randomRotation + 'deg)';
	}
}