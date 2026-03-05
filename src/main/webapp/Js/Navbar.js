// Js/Navbar.js

const sidebarPages = [
     { href: "Billing.html", icon: "fa-file-invoice", title: "Billing", feature: "ACCESS_BILLING_PAGE" },
    { href: "Reservation.html", icon: "fa-calendar-check", title: "Reservations", feature: "ACCESS_RESERVATIONPAGE" },
    { href: "Users.html", icon: "fa-users", title: "Users", feature: "ACCESS_USERSPAGE" },
     { href: "Rooms.html", icon: "fa-bed", title: "Rooms", feature: "ACCESS_ROOMPAGE" },
    { href: "Profile.html", icon: "fa-user-circle", title: "Profile", feature: "ACCESS_PROFILE_PAGE" },
   
];

const Prefix = "<a href='Dashboard.html' class='sidebar-nav-item'><i class='fas fa-home'></i><span>Dashboard</span></a>";
const Suffix = " <a href='Help.html' class='sidebar-nav-item'><i class='fas fa-question-circle'></i><span>Help</span></a>" +
               "<a href='Logout.html' id='logoutBtn' class='sidebar-nav-item'><i class='fas fa-sign-out-alt'></i><span>Logout</span></a>";

function populateSidebar() {
    const tvnavUSername = document.getElementById('navUsername');
    const tvnavRole= document.getElementById('navRoleid');
   
    if  ( tvnavUSername!=null && sessionStorage.getItem("userName")){
        tvnavUSername.textContent = sessionStorage.getItem("userName") || "Unknown User";
    }
    if( tvnavRole!=null && sessionStorage.getItem("userRole")){
        tvnavRole.textContent = sessionStorage.getItem("userRole") || "Unknown Role";   
    }
               

   // tvnavUSername.textContent = sessionStorage.getItem("username") || "Unknown User";
    //tvnavRole.textContent = sessionStorage.getItem("role") || "Unknown Role";   

    const nav = document.getElementById('sidebarNav');


    if (!nav) return;

    nav.innerHTML = Prefix;

    const currentPage = window.location.pathname.split("/").pop();

    sidebarPages.forEach(page => {
        if (hasFeature(page.feature)) {
            const a = document.createElement('a');
            a.href = page.href;
            a.className = "sidebar-nav-item";
            if (currentPage === page.href) a.classList.add('active');
            a.innerHTML = `<i class="fas ${page.icon}"></i><span>${page.title}</span>`;
            nav.appendChild(a);
        }
    });

    nav.innerHTML += Suffix;

    // Optional: logout button handler
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) logoutBtn.addEventListener('click', () => {
 
        window.location.href = "Logout.html";
    });
}

document.addEventListener('DOMContentLoaded', populateSidebar);