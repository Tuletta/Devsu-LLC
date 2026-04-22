import { Outlet, NavLink } from 'react-router-dom';
import { Users, CreditCard, Activity, FileText } from 'lucide-react';
import './MainLayout.css';

export const MainLayout = () => {
  const menuItems = [
    { path: '/clientes', icon: <Users size={20} />, label: 'Clientes' },
    { path: '/cuentas', icon: <CreditCard size={20} />, label: 'Cuentas' },
    { path: '/movimientos', icon: <Activity size={20} />, label: 'Movimientos' },
    { path: '/reportes', icon: <FileText size={20} />, label: 'Reportes' },
  ];

  return (
    <div className="app-container">
      {/* Sidebar */}
      <aside className="sidebar glass-panel">
        <div className="sidebar-header">
          <h1 className="logo-text">BANCO<span>DEVSU</span></h1>
        </div>
        
        <nav className="sidebar-nav">
          {menuItems.map((item) => (
            <NavLink 
              key={item.path} 
              to={item.path} 
              className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}
            >
              <span className="nav-icon">{item.icon}</span>
              <span className="nav-label">{item.label}</span>
            </NavLink>
          ))}
        </nav>
      </aside>

      {/* Main Content Area */}
      <main className="main-content">
        <div className="content-wrapper animate-fade-in">
          <Outlet />
        </div>
      </main>
    </div>
  );
};
