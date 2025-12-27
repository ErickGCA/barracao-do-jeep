import { Card, Statistic } from 'antd';
import { ArrowUpOutlined, ArrowDownOutlined } from '@ant-design/icons';
import PropTypes from 'prop-types';
import './StatCard.css';

const StatCard = ({
  title,
  value,
  prefix,
  suffix,
  icon,
  trend,
  trendValue,
  color = 'primary',
  loading = false,
  onClick,
}) => {
  const colorMap = {
    primary: '#1677ff',
    success: '#52c41a',
    warning: '#fa8c16',
    error: '#f5222d',
    purple: '#722ed1',
    cyan: '#13c2c2',
  };

  const bgColorMap = {
    primary: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    success: 'linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%)',
    warning: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
    error: 'linear-gradient(135deg, #d53369 0%, #daae51 100%)',
    purple: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
    cyan: 'linear-gradient(135deg, #30cfd0 0%, #330867 100%)',
  };

  const getTrendIcon = () => {
    if (!trend) return null;
    return trend === 'up' ? (
      <ArrowUpOutlined style={{ color: '#52c41a', fontSize: 16 }} />
    ) : (
      <ArrowDownOutlined style={{ color: '#f5222d', fontSize: 16 }} />
    );
  };

  return (
    <Card
      className={`stat-card stat-card--${color}`}
      loading={loading}
      hoverable={!!onClick}
      onClick={onClick}
      bodyStyle={{ padding: '24px' }}
    >
      <div className="stat-card__header">
        <div className="stat-card__icon" style={{ background: bgColorMap[color] }}>
          {icon}
        </div>
        {trend && (
          <div className="stat-card__trend">
            {getTrendIcon()}
            <span className="stat-card__trend-value">{trendValue}</span>
          </div>
        )}
      </div>

      <div className="stat-card__content">
        <Statistic
          title={title}
          value={value}
          prefix={prefix}
          suffix={suffix}
          valueStyle={{
            color: colorMap[color],
            fontSize: 32,
            fontWeight: 700,
            lineHeight: 1.2,
          }}
        />
      </div>
    </Card>
  );
};

StatCard.propTypes = {
  title: PropTypes.string.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  prefix: PropTypes.node,
  suffix: PropTypes.node,
  icon: PropTypes.node,
  trend: PropTypes.oneOf(['up', 'down']),
  trendValue: PropTypes.string,
  color: PropTypes.oneOf(['primary', 'success', 'warning', 'error', 'purple', 'cyan']),
  loading: PropTypes.bool,
  onClick: PropTypes.func,
};

export default StatCard;
