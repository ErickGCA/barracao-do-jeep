import { Card as AntCard } from 'antd';
import PropTypes from 'prop-types';
import './Card.css';

const Card = ({
  children,
  title,
  extra,
  bordered,
  hoverable = false,
  loading = false,
  size = 'default',
  className = '',
  headStyle,
  bodyStyle,
  styles,
  ...props
}) => {
  const variant = bordered === false ? 'borderless' : bordered === true ? 'outlined' : undefined;

  const cardStyles = styles || {
    header: headStyle,
    body: bodyStyle,
  };

  return (
    <AntCard
      title={title}
      extra={extra}
      variant={variant}
      hoverable={hoverable}
      loading={loading}
      size={size}
      className={`custom-card ${className}`}
      styles={cardStyles}
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
  styles: PropTypes.object,
};

export default Card;
