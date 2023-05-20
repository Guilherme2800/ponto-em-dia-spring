function showNavbar(toggleId, navId, bodyId, headerId) {
  const toggle = document.getElementById(toggleId);
  const nav = document.getElementById(navId);
  const bodypd = document.getElementById(bodyId);
  const headerpd = document.getElementById(headerId);

  if (toggle && nav && bodypd && headerpd) {
    toggle.addEventListener('click', () => {
      nav.classList.toggle('show');
      toggle.classList.toggle('bx-x');
      bodypd.classList.toggle('body-pd');
      headerpd.classList.toggle('body-pd');
    });
  }
}

function colorLink() {
  const linkColor = document.querySelectorAll('.nav_link');
  if (linkColor) {
    linkColor.forEach((l) => l.classList.remove('active'));
    this.classList.add('active');
  }
}

document.addEventListener('DOMContentLoaded', function (event) {
  showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header');
  const linkColor = document.querySelectorAll('.nav_link');
  linkColor.forEach((l) => l.addEventListener('click', colorLink));
});