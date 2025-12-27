import { Card as AntCard } from 'antd';
import PropTypes from 'prop-types';
import './Card.css';

const Card = ({
  children,
  title,
  extra,
  bordered = true,
  hoverable = false,
  loading = false,
  size = 'default',
  className = '',
  headStyle,
  bodyStyle,
  ...props
}) => {
  return (
    <AntCard
      title={title}
      extra={extra}
      bordered={bordered}
      hoverable={hoverable}
      loading={loading}
      size={size}
      className={`custom-card ${className}`}
      headStyle={headStyle}
      bodyStyle={bodyStyle}
      {...props}
    >
      {children}
    </AntCard>
  );
};

Card.propTypes = {
  children: PropTypes.node,
  title: PropTypes.node,
  extra: PropTypes.node,
  bordered: PropTypes.bool,
  hoverable: PropTypes.bool,
  loading: PropTypes.bool,
  size: PropTypes.oneOf(['default', 'small']),
  className: PropTypes.string,
  headStyle: PropTypes.object,
  bodyStyle: PropTypes.object,
};

export default Card;
