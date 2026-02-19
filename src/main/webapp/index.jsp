<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ocean View Resort - Property Management System</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        /* Professional Reset and Design System */
        :root {
            --primary: #0a4d7c;
            --primary-dark: #083b5e;
            --primary-light: #1e6aa5;
            --secondary: #2c7a4d;
            --accent: #c17b2c;
            --dark: #1e293b;
            --gray-800: #334155;
            --gray-600: #64748b;
            --gray-400: #94a3b8;
            --gray-200: #e2e8f0;
            --gray-100: #f1f5f9;
            --white: #ffffff;
            --shadow-sm: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
            --shadow-md: 0 4px 6px -1px rgba(0,0,0,0.1), 0 2px 4px -1px rgba(0,0,0,0.06);
            --shadow-lg: 0 10px 15px -3px rgba(0,0,0,0.1), 0 4px 6px -2px rgba(0,0,0,0.05);
            --shadow-xl: 0 20px 25px -5px rgba(0,0,0,0.1), 0 10px 10px -5px rgba(0,0,0,0.04);
            --radius-sm: 4px;
            --radius-md: 8px;
            --radius-lg: 12px;
            --transition: all 0.2s ease-in-out;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            background-color: var(--gray-100);
            color: var(--dark);
            line-height: 1.5;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
        }

        /* Header - Professional Corporate Style */
        header {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            padding: 1.5rem 2rem;
            border-bottom: 4px solid var(--secondary);
        }

        header h1 {
            color: var(--white);
            font-size: 1.875rem;
            font-weight: 600;
            letter-spacing: -0.5px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        header h1::before {
            content: "🌊";
            font-size: 2rem;
            filter: drop-shadow(2px 2px 4px rgba(0,0,0,0.2));
        }

        /* Navigation - Modern Dashboard Style */
        nav {
            background: var(--white);
            box-shadow: var(--shadow-sm);
            position: sticky;
            top: 0;
            z-index: 100;
            border-bottom: 1px solid var(--gray-200);
        }

        nav ul {
            max-width: 1400px;
            margin: 0 auto;
            padding: 0 2rem;
            list-style: none;
            display: flex;
            gap: 2rem;
            height: 64px;
            align-items: center;
        }

        nav ul li {
            position: relative;
            height: 100%;
            display: flex;
            align-items: center;
        }

        nav ul li a {
            color: var(--gray-600);
            text-decoration: none;
            font-weight: 500;
            font-size: 0.95rem;
            padding: 0.5rem 0;
            transition: var(--transition);
            letter-spacing: 0.3px;
            position: relative;
        }

        nav ul li a::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 0;
            height: 3px;
            background: linear-gradient(90deg, var(--primary), var(--secondary));
            transition: width 0.2s ease;
            border-radius: var(--radius-sm) var(--radius-sm) 0 0;
        }

        nav ul li a:hover {
            color: var(--primary);
        }

        nav ul li a:hover::after {
            width: 100%;
        }

        /* Main Container */
        main {
            max-width: 1400px;
            margin: 2.5rem auto;
            padding: 0 2rem;
        }

        /* Hero/Welcome Section */
        .welcome-section {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            border-radius: var(--radius-lg);
            padding: 3rem;
            margin-bottom: 3rem;
            color: var(--white);
            box-shadow: var(--shadow-lg);
            position: relative;
            overflow: hidden;
        }

        .welcome-section::before {
            content: '';
            position: absolute;
            top: 0;
            right: 0;
            width: 400px;
            height: 400px;
            background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 70%);
            transform: translate(100px, -100px);
        }

        .welcome-section::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 300px;
            height: 300px;
            background: radial-gradient(circle, rgba(255,255,255,0.05) 0%, transparent 70%);
            transform: translate(-100px, 100px);
        }

        .welcome-section h2 {
            font-size: 2.5rem;
            font-weight: 700;
            margin-bottom: 1rem;
            letter-spacing: -1px;
            position: relative;
            z-index: 1;
        }

        .welcome-section p {
            font-size: 1.125rem;
            opacity: 0.9;
            max-width: 600px;
            position: relative;
            z-index: 1;
        }

        .stats-container {
            display: flex;
            gap: 2rem;
            margin-top: 2rem;
            position: relative;
            z-index: 1;
        }

        .stat-item {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            padding: 1rem 2rem;
            border-radius: var(--radius-md);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .stat-value {
            font-size: 1.875rem;
            font-weight: 700;
            line-height: 1;
        }

        .stat-label {
            font-size: 0.875rem;
            opacity: 0.8;
            margin-top: 0.25rem;
        }

        /* Card Grid - Professional Dashboard Style */
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
            gap: 1.5rem;
            margin-bottom: 2.5rem;
        }

        .card {
            background: var(--white);
            border-radius: var(--radius-lg);
            overflow: hidden;
            box-shadow: var(--shadow-md);
            transition: var(--transition);
            border: 1px solid var(--gray-200);
            display: flex;
            flex-direction: column;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: var(--shadow-xl);
            border-color: var(--primary-light);
        }

        .card-header {
            padding: 1.5rem 1.5rem 0.75rem 1.5rem;
            border-bottom: 1px solid var(--gray-200);
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .card-icon {
            width: 48px;
            height: 48px;
            background: linear-gradient(135deg, var(--primary-light) 0%, var(--primary) 100%);
            border-radius: var(--radius-md);
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--white);
            font-size: 1.5rem;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: var(--dark);
            margin: 0;
        }

        .card-content {
            padding: 1.5rem;
            flex-grow: 1;
        }

        .card-content p {
            color: var(--gray-600);
            font-size: 0.95rem;
            margin-bottom: 1rem;
        }

        .feature-list {
            list-style: none;
            margin-bottom: 1.5rem;
        }

        .feature-list li {
            color: var(--gray-600);
            font-size: 0.9rem;
            padding: 0.35rem 0;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .feature-list li::before {
            content: "✓";
            color: var(--secondary);
            font-weight: 700;
        }

        .card-footer {
            padding: 1rem 1.5rem 1.5rem 1.5rem;
            background: var(--gray-100);
            border-top: 1px solid var(--gray-200);
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            padding: 0.625rem 1.25rem;
            border-radius: var(--radius-md);
            font-weight: 500;
            font-size: 0.95rem;
            text-decoration: none;
            transition: var(--transition);
            cursor: pointer;
            border: none;
            width: 100%;
        }

        .btn-primary {
            background: linear-gradient(135deg, var(--primary) 0%, var(--primary-dark) 100%);
            color: var(--white);
            box-shadow: var(--shadow-sm);
        }

        .btn-primary:hover {
            background: linear-gradient(135deg, var(--primary-light) 0%, var(--primary) 100%);
            box-shadow: var(--shadow-md);
            transform: translateY(-1px);
        }

        .btn-secondary {
            background: var(--white);
            color: var(--primary);
            border: 1px solid var(--primary);
        }

        .btn-secondary:hover {
            background: var(--gray-100);
            border-color: var(--primary-dark);
        }

        /* Quick Actions Section */
        .quick-actions {
            background: var(--white);
            border-radius: var(--radius-lg);
            padding: 2rem;
            margin-top: 2rem;
            box-shadow: var(--shadow-md);
            border: 1px solid var(--gray-200);
        }

        .quick-actions h3 {
            font-size: 1.25rem;
            font-weight: 600;
            color: var(--dark);
            margin-bottom: 1.5rem;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .action-buttons {
            display: flex;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .action-btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 0.75rem 1.5rem;
            background: var(--gray-100);
            border: 1px solid var(--gray-200);
            border-radius: var(--radius-md);
            color: var(--gray-600);
            text-decoration: none;
            font-weight: 500;
            font-size: 0.95rem;
            transition: var(--transition);
        }

        .action-btn:hover {
            background: var(--primary);
            border-color: var(--primary);
            color: var(--white);
            transform: translateY(-2px);
            box-shadow: var(--shadow-md);
        }

        /* Footer */
        footer {
            background: var(--dark);
            color: var(--white);
            padding: 3rem 2rem 2rem;
            margin-top: 4rem;
            border-top: 4px solid var(--primary);
        }

        .footer-content {
            max-width: 1400px;
            margin: 0 auto;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 3rem;
        }

        .footer-section h4 {
            font-size: 1rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: var(--gray-400);
            letter-spacing: 0.5px;
            text-transform: uppercase;
        }

        .footer-section p, .footer-section a {
            color: var(--gray-400);
            font-size: 0.95rem;
            line-height: 1.6;
            text-decoration: none;
            transition: var(--transition);
        }

        .footer-section a:hover {
            color: var(--white);
        }

        .contact-info {
            list-style: none;
        }

        .contact-info li {
            margin-bottom: 0.75rem;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .copyright {
            text-align: center;
            padding-top: 2rem;
            margin-top: 2rem;
            border-top: 1px solid var(--gray-800);
            color: var(--gray-400);
            font-size: 0.875rem;
        }

        /* Status Indicators */
        .status-badge {
            display: inline-flex;
            align-items: center;
            padding: 0.25rem 0.75rem;
            border-radius: 999px;
            font-size: 0.75rem;
            font-weight: 500;
            background: var(--gray-200);
            color: var(--gray-600);
        }

        .status-badge.success {
            background: #d1fae5;
            color: #065f46;
        }

        .status-badge.warning {
            background: #fed7aa;
            color: #92400e;
        }

        /* Responsive Design */
        @media (max-width: 1024px) {
            .welcome-section h2 {
                font-size: 2rem;
            }
        }

        @media (max-width: 768px) {
            header h1 {
                font-size: 1.5rem;
            }

            nav ul {
                padding: 0 1rem;
                gap: 1rem;
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
            }

            nav ul li a {
                white-space: nowrap;
            }

            .welcome-section {
                padding: 2rem;
            }

            .welcome-section h2 {
                font-size: 1.5rem;
            }

            .stats-container {
                flex-direction: column;
                gap: 1rem;
            }

            .dashboard-grid {
                grid-template-columns: 1fr;
            }

            .footer-content {
                grid-template-columns: 1fr;
                gap: 2rem;
            }
        }

        @media (max-width: 480px) {
            main {
                padding: 0 1rem;
            }

            .welcome-section {
                padding: 1.5rem;
            }

            .action-buttons {
                flex-direction: column;
            }

            .action-btn {
                width: 100%;
                justify-content: center;
            }
        }

        /* Loading Animation */
        .skeleton {
            background: linear-gradient(90deg, var(--gray-200) 25%, var(--gray-100) 50%, var(--gray-200) 75%);
            background-size: 200% 100%;
            animation: loading 1.5s infinite;
        }

        @keyframes loading {
            0% { background-position: 200% 0; }
            100% { background-position: -200% 0; }
        }

        /* Tooltip */
        [data-tooltip] {
            position: relative;
            cursor: help;
        }

        [data-tooltip]:before {
            content: attr(data-tooltip);
            position: absolute;
            bottom: 100%;
            left: 50%;
            transform: translateX(-50%);
            padding: 0.5rem 1rem;
            background: var(--dark);
            color: var(--white);
            font-size: 0.75rem;
            border-radius: var(--radius-sm);
            white-space: nowrap;
            opacity: 0;
            visibility: hidden;
            transition: var(--transition);
            z-index: 1000;
        }

        [data-tooltip]:hover:before {
            opacity: 1;
            visibility: visible;
            bottom: calc(100% + 5px);
        }
    </style>
</head>
<body>
<header>
    <h1>Ocean View Resort - Property Management System</h1>
</header>

<nav>
    <ul>
        <li><a href="dashboard.jsp">Dashboard</a></li>
        <li><a href="reservation" class="active">Reservations</a></li>
        <li><a href="guest-form.jsp">Guests</a></li>
        <li><a href="room-form.jsp">Rooms</a></li>
        <li><a href="bill-form.jsp">Billing</a></li>
        <li><a href="reports.jsp">Reports</a></li>
        <li><a href="help.jsp">Help</a></li>
    </ul>
</nav>

<main>
    <div class="welcome-section">
        <h2>Good afternoon, Administrator</h2>
        <p>Manage your property efficiently with real-time updates and comprehensive controls.</p>

        <div class="stats-container">
            <div class="stat-item">
                <div class="stat-value">24</div>
                <div class="stat-label">Active Reservations</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">12</div>
                <div class="stat-label">Available Rooms</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">85%</div>
                <div class="stat-label">Occupancy Rate</div>
            </div>
        </div>
    </div>

    <div class="dashboard-grid">
        <!-- New Reservation Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon">📝</div>
                <h3 class="card-title">New Reservation</h3>
            </div>
            <div class="card-content">
                <p>Create a new booking with our streamlined reservation process.</p>
                <ul class="feature-list">
                    <li>Real-time room availability</li>
                    <li>Guest preferences</li>
                    <li>Special requests handling</li>
                </ul>
            </div>
            <div class="card-footer">
                <a href="reservation-form.jsp" class="btn btn-primary">
                    <span>Create Reservation</span>
                    <span>→</span>
                </a>
            </div>
        </div>

        <!-- View Reservations Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon">🔍</div>
                <h3 class="card-title">Reservations</h3>
            </div>
            <div class="card-content">
                <p>Manage and monitor all current and upcoming bookings.</p>
                <ul class="feature-list">
                    <li>Check-in/out management</li>
                    <li>Modify reservations</li>
                    <li>Cancellation handling</li>
                </ul>
                <div style="margin-top: 1rem;">
                    <span class="status-badge success">12 Check-ins today</span>
                </div>
            </div>
            <div class="card-footer">
                <a href="reservation" class="btn btn-secondary">
                    <span>View All</span>
                </a>
            </div>
        </div>

        <!-- Billing Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon">💰</div>
                <h3 class="card-title">Billing</h3>
            </div>
            <div class="card-content">
                <p>Generate invoices and process payments securely.</p>
                <ul class="feature-list">
                    <li>Automated bill calculation</li>
                    <li>Multiple payment methods</li>
                    <li>Tax management</li>
                </ul>
                <div style="margin-top: 1rem;">
                    <span class="status-badge warning">3 pending payments</span>
                </div>
            </div>
            <div class="card-footer">
                <a href="bill-form.jsp" class="btn btn-secondary">
                    <span>Generate Bill</span>
                </a>
            </div>
        </div>

        <!-- Reports Card -->
        <div class="card">
            <div class="card-header">
                <div class="card-icon">📊</div>
                <h3 class="card-title">Analytics</h3>
            </div>
            <div class="card-content">
                <p>Access detailed reports and performance metrics.</p>
                <ul class="feature-list">
                    <li>Revenue analysis</li>
                    <li>Occupancy trends</li>
                    <li>Guest demographics</li>
                </ul>
            </div>
            <div class="card-footer">
                <a href="reports.jsp" class="btn btn-secondary">
                    <span>View Reports</span>
                </a>
            </div>
        </div>
    </div>

    <!-- Quick Actions Section -->
    <div class="quick-actions">
        <h3>
            <span>⚡</span>
            Quick Actions
        </h3>
        <div class="action-buttons">
            <a href="check-in.jsp" class="action-btn" data-tooltip="Process guest check-in">
                <span>🚪</span> Quick Check-in
            </a>
            <a href="check-out.jsp" class="action-btn" data-tooltip="Process guest check-out">
                <span>🧾</span> Quick Check-out
            </a>
            <a href="room-status.jsp" class="action-btn" data-tooltip="Update room status">
                <span>🛏️</span> Room Status
            </a>
            <a href="housekeeping.jsp" class="action-btn" data-tooltip="Housekeeping requests">
                <span>🧹</span> Housekeeping
            </a>
            <a href="maintenance.jsp" class="action-btn" data-tooltip="Maintenance requests">
                <span>🔧</span> Maintenance
            </a>
        </div>
    </div>
</main>

<footer>
    <div class="footer-content">
        <div class="footer-section">
            <h4>Ocean View Resort</h4>
            <p>Luxury redefined with exceptional service and breathtaking ocean views. Experience unparalleled comfort and professional hospitality.</p>
        </div>
        <div class="footer-section">
            <h4>Contact</h4>
            <ul class="contact-info">
                <li>📍 123 Beach Road, Paradise Island</li>
                <li>📞 +1 (555) 123-4567</li>
                <li>✉️ reservations@oceanview.com</li>
            </ul>
        </div>
        <div class="footer-section">
            <h4>Quick Links</h4>
            <p><a href="about.jsp">About Us</a></p>
            <p><a href="careers.jsp">Careers</a></p>
            <p><a href="privacy.jsp">Privacy Policy</a></p>
        </div>
    </div>
    <div class="copyright">
        &copy; 2026 Ocean View Resort Property Management System. All rights reserved. | Version 2.1.0 | Developed by CJ Enterprise Solutions
    </div>
</footer>

<script>
    // Professional JavaScript for enhanced functionality
    (function() {
        'use strict';

        // Update greeting based on time
        function updateGreeting() {
            const hour = new Date().getHours();
            const greetingElement = document.querySelector('.welcome-section h2');
            let greeting = '';

            if (hour < 12) {
                greeting = 'Good morning';
            } else if (hour < 18) {
                greeting = 'Good afternoon';
            } else {
                greeting = 'Good evening';
            }

            if (greetingElement) {
                greetingElement.textContent = `${greeting}, Administrator`;
            }
        }

        // Highlight active navigation item
        function setActiveNav() {
            const currentPath = window.location.pathname.split('/').pop() || 'dashboard.jsp';
            const navLinks = document.querySelectorAll('nav ul li a');

            navLinks.forEach(link => {
                const linkPath = link.getAttribute('href');
                if (linkPath === currentPath) {
                    link.classList.add('active');
                    link.setAttribute('aria-current', 'page');
                }
            });
        }

        // Add smooth scrolling for anchor links
        function initSmoothScroll() {
            document.querySelectorAll('a[href^="#"]').forEach(anchor => {
                anchor.addEventListener('click', function(e) {
                    e.preventDefault();
                    const target = document.querySelector(this.getAttribute('href'));
                    if (target) {
                        target.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start'
                        });
                    }
                });
            });
        }

        // Initialize tooltips
        function initTooltips() {
            const tooltips = document.querySelectorAll('[data-tooltip]');
            tooltips.forEach(element => {
                element.addEventListener('mouseenter', (e) => {
                    // Tooltip handling is done via CSS, but we can add additional functionality here
                });
            });
        }

        // Add keyboard shortcuts
        document.addEventListener('keydown', (e) => {
            // Ctrl + N for new reservation
            if (e.ctrlKey && e.key === 'n') {
                e.preventDefault();
                window.location.href = 'reservation-form.jsp';
            }
            // Ctrl + R for reservations list
            if (e.ctrlKey && e.key === 'r') {
                e.preventDefault();
                window.location.href = 'reservation';
            }
        });

        // Initialize all functions
        document.addEventListener('DOMContentLoaded', () => {
            updateGreeting();
            setActiveNav();
            initSmoothScroll();
            initTooltips();
        });

        // Refresh stats periodically (simulated)
        setInterval(() => {
            // In a real application, this would fetch updated data via AJAX
            console.log('Refreshing dashboard data...');
        }, 30000); // Refresh every 30 seconds

    })();
</script>
</body>
</html>