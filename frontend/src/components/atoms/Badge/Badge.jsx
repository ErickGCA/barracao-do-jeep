import { Badge as AntBadge } from 'antd';
import PropTypes from 'prop-types';
import './Badge.css';

const Badge = ({
  children,
  count,
  dot = false,
  status,
  color,
  text,
  showZero = false,
  overflowCount = 99,
  className = '',
  ...props
}) => {
  return (
    <AntBadge
      count={count}
      dot={dot}
      status={status}
      color={color}
      text={text}
      showZero={showZero}
      overflowCount={overflowCount}
      className={`custom-badge ${className}`}
      {...props}
    >
      {children}
    </AntBadge>
  );
};

Badge.propTypes = {
  children: PropTypes.node,
  count: PropTypes.oneOfType([PropTypes.number, PropTypes.node]),
  dot: PropTypes.bool,
  status: PropTypes.oneOf(['success', 'processing', 'default', 'error', 'warning']),
  color: PropTypes.string,
  text: PropTypes.node,
  showZero: PropTypes.bool,
  overflowCount: PropTypes.number,
  className: PropTypes.string,
};

export default Badge;
